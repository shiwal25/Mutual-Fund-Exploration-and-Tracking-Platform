package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository

class ViewModelFactory(
    private val mutualFundRepository: MutualFundRepository,
    private  val watchListDao: WatchListDao
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ExploreViewModel::class.java) ->
                ExploreViewModel(mutualFundRepository) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(mutualFundRepository) as T

            modelClass.isAssignableFrom(WatchlistViewModel::class.java) ->
                WatchlistViewModel(watchListDao) as T

            modelClass.isAssignableFrom(FundDetailViewModel::class.java) ->
                FundDetailViewModel(mutualFundRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}