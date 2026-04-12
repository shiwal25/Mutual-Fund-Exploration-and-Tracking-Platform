package com.example.mutualfundexplorationandtrackingplatform.data.repository

import androidx.paging.PagingData
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.models.NavPoint
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import kotlinx.coroutines.flow.Flow

interface MutualFundRepository {

    val mutalFundPagingFlow: Flow<PagingData<MutualFundDTO>>

    suspend fun fetchAndCacheDetails(schemeCode: Int?): Result<MutualFundDetail>

    fun observeFundByschemeCode(schemeCode: Int?): Flow<MutualFundDetail?>

    suspend fun fetchAndCacheCategoryFunds(category: String): Result<List<MutualFundDetail>>

    fun observeCategoryFunds(category: String): Flow<List<MutualFundDetail>>

    suspend fun fetchNavData(schemeCode: String, startDate: String? = null, endDate: String? = null): Result<List<NavPoint>>

    suspend fun searchFunds(query: String): List<MutualFundDTO>
}