package com.example.mutualfundexplorationandtrackingplatform.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mutualfundexplorationandtrackingplatform.data.MutualFundPagingSource
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntity2
import kotlinx.coroutines.flow.Flow

class MFRepository(private val mutualFundDAO: MutualFundDAO,
                   private val mutualFundApiService: MutualFundApiService
) : MutualFundRepository {

    override val mutalFundPagingFlow: Flow<PagingData<MutualFundDTO>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MutualFundPagingSource(
            mutualFundApiService,
            dao = mutualFundDAO
        ) }
    ).flow

    override suspend fun fetchAndCacheDetails(schemeCode: Int?): Result<MutualFundDetail> {
        Log.d("Repository", "fetchAndCacheDetails called for schemeCode=$schemeCode")  // ADD THIS

        val cached = mutualFundDAO.getFundByScheme(schemeCode)
        Log.d("Repository", "Cached: ${cached?.schemeCode}, detailsIsFetched=${cached?.detailsIsFetched}")  // ADD THIS

        if (cached != null && cached.detailsIsFetched) {
            Log.d("Repository", "Returning cached data")  // ADD THIS
            return Result.success(cached)
        }

        return try {
            Log.d("Repository", "Calling API for details")  // ADD THIS
            val dto = mutualFundApiService.getFundDetails(schemeCode)
            Log.d("Repository", "API response: meta=${dto.meta}, data size=${dto.data.size}")  // ADD THIS

            val entity = dto.toEntity2()
            Log.d("Repository", "Mapped entity: latestNav=${entity.latestNav}, detailsIsFetched=${entity.detailsIsFetched}")  // ADD THIS

            mutualFundDAO.updateFundDetails(
                schemeCode = schemeCode,
                fundHouse = entity.fundHouse,
                schemeType = entity.schemeType,
                schemeCategory = entity.schemeCategory,
                latestNav = entity.latestNav,
                latestNavDate = entity.latestNavDate,
                isInGrowth = entity.isInGrowth,
                isInDivReinvestment = entity.isInDivReinvestment
            )
            Log.d("Repository", "DB update called")

            val updated = mutualFundDAO.getFundByScheme(schemeCode)
                ?: error("Row missing after update — should never happen")
            Log.d("Repository", "Updated entity from DB: latestNav=${updated.latestNav}, detailsIsFetched=${updated.detailsIsFetched}")  // ADD THIS

            Result.success(updated)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching details: ${e.message}", e)  // ADD THIS
            val stale = mutualFundDAO.getFundByScheme(schemeCode)
            if (stale != null) {
                Result.success(stale)
            } else {
                Result.failure(e)
            }
        }
    }

    override fun observeFundByschemeCode(schemeCode: Int?): Flow<MutualFundDetail?> =
        mutualFundDAO.observeFundByScheme(schemeCode)
    }