package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchList
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchListFundCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val watchlistDao: WatchListDao
) : ViewModel() {

    // 1. Expose all watchlists as a state flow
    val allWatchlists: StateFlow<List<WatchList>> = watchlistDao.getAllWatchlists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    // 2. Get which watchlists contain the currently viewed fund
    fun getWatchlistsForFund(schemeCode: Int?): Flow<List<Long>> {
        if (schemeCode == null) return flowOf(emptyList())
        return watchlistDao.getWatchlistsContainingFund(schemeCode)
    }

    // 3. Create a new Watchlist
    fun addWatchlist(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            watchlistDao.insertWatchlist(WatchList(name = name.trim()))
        }
    }

    // 4. Toggle fund in a watchlist
    fun toggleFundInWatchlist(watchlistId: Long, schemeCode: Int, isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                watchlistDao.addFundToWatchlist(WatchListFundCrossRef(watchlistId, schemeCode))
            } else {
                watchlistDao.removeFundFromWatchlist(watchlistId, schemeCode)
            }
        }
    }
}