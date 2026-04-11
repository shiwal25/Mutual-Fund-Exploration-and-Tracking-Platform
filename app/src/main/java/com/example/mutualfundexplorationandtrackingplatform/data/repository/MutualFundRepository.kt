package com.example.mutualfundexplorationandtrackingplatform.data.repository

import androidx.paging.PagingData
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.NavHistory
import kotlinx.coroutines.flow.Flow

interface MutualFundRepository {

    fun getFundsPager(): Flow<PagingData<MutualFund>>

    fun getFundDetail(schemeCode: Int): Flow<MutualFundDetail?>
    suspend fun refreshFundDetail(schemeCode: Int)

    fun getNavHistory(schemeCode: Int): Flow<List<NavHistory>>
    suspend fun refreshNavHistory(schemeCode: Int, startDate: String, endDate: String)
}