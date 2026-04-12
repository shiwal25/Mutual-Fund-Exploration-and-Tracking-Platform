package com.example.mutualfundexplorationandtrackingplatform.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.paging.MutualFundPagingSource
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.NavPoint
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntity
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntity2
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntityWithCategory
import kotlinx.coroutines.flow.Flow

class MutualFundRepositoryImpl(private val mutualFundDAO: MutualFundDAO,
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

        val cached = mutualFundDAO.getFundByScheme(schemeCode)

        if (cached != null && cached.detailsIsFetched) {
            return Result.success(cached)
        }

        return try {
            val dto = mutualFundApiService.getFundDetails(schemeCode)

            val entity = dto.toEntity2()

            mutualFundDAO.updateFundDetails(
                schemeCode = schemeCode,
                fundHouse = entity.fundHouse,
                schemeType = entity.schemeType,
                latestNav = entity.latestNav,
                latestNavDate = entity.latestNavDate,
                isInGrowth = entity.isInGrowth,
                isInDivReinvestment = entity.isInDivReinvestment
            )

            val updated = mutualFundDAO.getFundByScheme(schemeCode)
                ?: error("Data missing")

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

override suspend fun fetchAndCacheCategoryFunds(category: String): Result<List<MutualFundDetail>> {
    return try {
        val searchQuery = when(category) {
            "indexfunds" -> "index"
            "bluechip" -> "bluechip"
            "taxsaver" -> "tax"
            "largecap" -> "large"
            else -> category
        }

        val dtos = mutualFundApiService.getFundsByCategory(searchQuery)

        if (dtos.isEmpty()) {

            return Result.success(emptyList())
        }

        val entities = dtos.map { it.toEntityWithCategory(category) }

        mutualFundDAO.insertFunds(entities)
        val saved = mutualFundDAO.getFundsByCategory(category)

        Result.success(saved)
    } catch (e: Exception) {

        val cached = mutualFundDAO.getFundsByCategory(category)
        if (cached.isNotEmpty()) {
            Result.success(cached)
        } else {
            Result.failure(e)
        }
    }
}

    override suspend fun fetchNavData(
        schemeCode: String,
        startDate: String?,
        endDate: String?
    ): Result<List<NavPoint>> {
        return try {
            val response = mutualFundApiService.getNavData(schemeCode, startDate, endDate)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchFunds(query: String): List<MutualFundDTO> {
        return try {
            val dtos = mutualFundApiService.getFundsByCategory(query)
            val entities = dtos.map { it.toEntity() }
            mutualFundDAO.insertFunds(entities)
            dtos
        } catch (e: Exception) {
            throw e
        }
    }

    override fun observeCategoryFunds(category: String): Flow<List<MutualFundDetail>> =
        mutualFundDAO.observeFundsByCategory(category)
}