package com.example.mutualfundexplorationandtrackingplatform.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.FundRemoteKey
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.local.MutualFundDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntity
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FundRemoteMediator(
    private val db: MutualFundDatabase,
    private val api: MutualFundApiService
) : RemoteMediator<Int, MutualFund>() {

    private val fundDao = db.mutualFundDAO()
    private val keyDao = db.fundRemoteKeyDAO()

    override suspend fun initialize(): InitializeAction {
        // refreshing data older than 1hour
        val oldestKey = keyDao.getKey(0)
        return if (oldestKey == null) InitializeAction.LAUNCH_INITIAL_REFRESH
        else InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MutualFund>
    ): MediatorResult {

        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                // Get next offset from remote key stored for the last item
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                keyDao.getKey(lastItem.schemeCode)?.nextOffset
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = retryWithBackoff {
                api.getMutualFunds(limit = state.config.pageSize, offset = offset)
            }

            val endOfPagination = response.size < state.config.pageSize

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    keyDao.clearAll()
                    fundDao.clearAll()
                }

                val nextOffset = if (endOfPagination) null else offset + response.size

                val keys = response.map { fund ->
                    FundRemoteKey(
                        schemeCode = fund.schemeCode,
                        prevOffset = if (offset == 0) null else offset,
                        nextOffset = nextOffset
                    )
                }

                keyDao.insertAll(keys)
                fundDao.insertAll(response.map { it.toEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

suspend fun <T> retryWithBackoff(
    maxRetries: Int = 3,
    initialDelay: Long = 500L,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(maxRetries - 1) { attempt ->
        try {
            return block()
        } catch (e: Exception) {
            if (attempt == maxRetries - 2) throw e
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong()
    }
    return block()
}