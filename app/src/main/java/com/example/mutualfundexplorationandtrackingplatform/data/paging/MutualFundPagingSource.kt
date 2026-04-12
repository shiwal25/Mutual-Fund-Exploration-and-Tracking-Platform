package com.example.mutualfundexplorationandtrackingplatform.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntity

class MutualFundPagingSource(
    private val apiService: MutualFundApiService,
    private val dao: MutualFundDAO  // Add DAO parameter
) : PagingSource<Int, MutualFundDTO>() {

override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MutualFundDTO> {
    val offset = params.key ?: 0
    return try {
        // Try API first
        val items = apiService.getMutualFunds(limit = params.loadSize, offset = offset)
        val entities = items.map { it.toEntity() }
        dao.insertFunds(entities)
        LoadResult.Page(
            data = items,
            prevKey = if (offset == 0) null else offset - params.loadSize,
            nextKey = if (items.size < params.loadSize) null else offset + params.loadSize
        )
    } catch (e: Exception) {
        // On error, try loading from database
        try {
            val cachedItems = dao.getAllFunds(limit = params.loadSize, offset = offset)
            if (cachedItems.isNotEmpty()) {
                // Convert entities back to DTOs for display
                val dtos = cachedItems.map { it.toDTO() }
                LoadResult.Page(
                    data = dtos,
                    prevKey = if (offset == 0) null else offset - params.loadSize,
                    nextKey = if (cachedItems.size < params.loadSize) null else offset + params.loadSize
                )
            } else {
                LoadResult.Error(e)
            }
        } catch (dbError: Exception) {
            LoadResult.Error(e)
        }
    }
}

    override fun getRefreshKey(state: PagingState<Int, MutualFundDTO>): Int? {
        return state.anchorPosition?.let { anchor ->
            maxOf(0, anchor - (state.config.pageSize / 2))
        }
    }
}