package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchList
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.watchListDataEntity.WatchListFundCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val watchlistDao: WatchListDao
) : ViewModel() {

    /*TODO instead of getting data from DAO get ot from repository*/
    val allWatchlists: StateFlow<List<WatchList>> = watchlistDao.getAllWatchlists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun getWatchlistsForFund(schemeCode: Int?): Flow<List<Long>> {
        if (schemeCode == null) return flowOf(emptyList())
        return watchlistDao.getWatchlistsContainingFund(schemeCode)
    }

    fun addWatchlist(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            watchlistDao.insertWatchlist(WatchList(name = name.trim()))
        }
    }

    fun toggleFundInWatchlist(watchlistId: Long, schemeCode: Int, isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                watchlistDao.addFundToWatchlist(WatchListFundCrossRef(watchlistId, schemeCode))
            } else {
                watchlistDao.removeFundFromWatchlist(watchlistId, schemeCode)
            }
        }
    }

    fun getFundsInWatchlist(watchlistId: Long): Flow<List<MutualFundDetail>> {
        return watchlistDao.getFundsInWatchlist(watchlistId)
    }
}