package com.example.mutualfundexplorationandtrackingplatform.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mutualfundexplorationandtrackingplatform.data.MutualFundPagingSource
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import kotlinx.coroutines.flow.Flow

class MFRepository(private val mutualFundDAO: MutualFundDAO,
                   private val mutualFundApiService: MutualFundApiService
) : MutualFundRepository {

    override val mutalFundPagingFlow: Flow<PagingData<MutualFundDTO>> = Pager(
        config = PagingConfig(
            pageSize = 100,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MutualFundPagingSource(mutualFundApiService) }
    ).flow

    override suspend fun fetchAndCacheDetails(schemeCode: Int?): Result<MutualFundDetail> {
        val cached = mutualFundDAO.getFundByScheme(schemeCode)
        if (cached != null && cached.detailsIsFetched) {
            return Result.success(cached)
        }

        return try {
            val dto = mutualFundApiService.getFundDetails(schemeCode)
            mutualFundDAO.updateFundDetails(
                schemeCode = schemeCode,
                fundHouse = dto.fundHouse,
                schemeType = dto.schemeType,
                schemeCategory = dto.schemeCategory,
                latestNav = dto.latestNav,
                latestNavDate = dto.latestNavDate,
                isInGrowth = dto.isInGrowth,
                isInDivReinvestment = dto.isInDivReinvestment
            )

            val updated = mutualFundDAO.getFundByScheme(schemeCode)
                ?: error("Row missing after update — should never happen")
            Result.success(updated)
        } catch (e: Exception) {
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