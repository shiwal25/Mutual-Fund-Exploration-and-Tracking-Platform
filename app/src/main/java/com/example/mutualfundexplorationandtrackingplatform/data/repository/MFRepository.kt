package com.example.mutualfundexplorationandtrackingplatform.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.NavHistory
import com.example.mutualfundexplorationandtrackingplatform.data.local.MutualFundDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.paging.FundRemoteMediator
import com.example.mutualfundexplorationandtrackingplatform.data.paging.retryWithBackoff
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import kotlinx.coroutines.flow.Flow

class MFRepository(private val mutualFundDatabase: MutualFundDatabase,
                   private val mutualFundApiService: MutualFundApiService
) : MutualFundRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getFundsPager(): Flow<PagingData<MutualFund>> = Pager(
        config = PagingConfig(pageSize = 100, enablePlaceholders = false),
        remoteMediator = FundRemoteMediator(mutualFundDatabase, mutualFundApiService),
        pagingSourceFactory = { mutualFundDatabase.mutualFundDAO().getAllFundsPaged() }
    ).flow

    override fun getFundDetail(schemeCode: Int): Flow<MutualFundDetail?> =
        mutualFundDatabase.mutualFundDetailDAO().getFundDetail(schemeCode)

    override suspend fun refreshFundDetail(schemeCode: Int) {
        try {
            val response = retryWithBackoff { mutualFundApiService.getLatestNav(schemeCode) }
            val latest = response.data.firstOrNull() ?: return
            mutualFundDatabase.mutualFundDetailDAO().insert(
                MutualFundDetail(
                    schemeCode = schemeCode,
                    fundHouse = response.meta.fundHouse,
                    schemeType = response.meta.schemeType,
                    schemeCategory = response.meta.schemeCategory,
                    schemeName = response.meta.schemeName,
                    latestNav = latest.nav,
                    latestNavDate = latest.date
                )
            )
        } catch (e: Exception) {
            // Serve stale Room data — no crash
        }
    }

    override fun getNavHistory(schemeCode: Int): Flow<List<NavHistory>> =
        mutualFundDatabase.navHistoryDAO().getNavHistory(schemeCode)

    override suspend fun refreshNavHistory(
        schemeCode: Int,
        startDate: String,
        endDate: String
    ) {
        try {
            val response = retryWithBackoff {
                mutualFundApiService.getNavHistory(schemeCode, startDate, endDate)
            }
            val entries = response.data.map {
                NavHistory(
                    schemeCode = schemeCode,
                    date = it.date,
                    nav = it.nav.toDoubleOrNull() ?: 0.0
                )
            }
            mutualFundDatabase.withTransaction {
                mutualFundDatabase.navHistoryDAO().clearForFund(schemeCode)
                mutualFundDatabase.navHistoryDAO().insertAll(entries)
            }
        } catch (e: Exception) {
            // Serve cached history
        }
    }
}