package com.example.mutualfundexplorationandtrackingplatform.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MutualFundRepository {

    fun getFundsPager(): Flow<PagingData<MutualFund>>

    fun getFundDetail(schemeCode: Int): Flow<MutualFundDetail?>
    suspend fun refreshFundDetail(schemeCode: Int)

    fun getNavHistory(schemeCode: Int): Flow<List<NavHistory>>
    suspend fun refreshNavHistory(schemeCode: Int, startDate: String, endDate: String)
}