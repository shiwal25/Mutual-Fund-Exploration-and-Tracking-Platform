package com.example.mutualfundexplorationandtrackingplatform.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO

class MutualFundPagingSource(
    private val apiService: MutualFundApiService
) : PagingSource<Int, MutualFundDTO>() {

    override fun getRefreshKey(state: PagingState<Int, MutualFundDTO>): Int? {
        return state.anchorPosition?.let { anchor ->
            maxOf(0, anchor - (state.config.pageSize / 2))
        }
    }

override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MutualFundDTO> {
    val offset = params.key ?: 0
    return try {
        val items = apiService.getMutualFunds(limit = 10, offset = 2) /*TODO limit and offset custom pass krna h*/
        LoadResult.Page(
            data    = items,
            prevKey = if (offset == 0) null else offset - params.loadSize,
            nextKey = if (items.size < params.loadSize) null else offset + params.loadSize
        )
    } catch (e: Exception) {
        Log.e("PagingSource", "Load failed", e)   // ← ADD THIS LINE
        LoadResult.Error(e)
    }
}
}