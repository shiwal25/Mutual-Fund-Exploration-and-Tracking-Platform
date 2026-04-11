package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.DetailUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class ExploreViewModel(    private val mutualFundRepository: MutualFundRepository
): ViewModel() {

    val mutalFundPagingFlow = mutualFundRepository.mutalFundPagingFlow
        .cachedIn(viewModelScope)

    private val inFlightJobs = ConcurrentHashMap<Int, Job>()

    fun requestDetail(schemeCode: Int?) {
        if (inFlightJobs.containsKey(schemeCode)) return

        val job = viewModelScope.launch {
            try {
                mutualFundRepository.fetchAndCacheDetails(schemeCode)
            } finally {
                inFlightJobs.remove(schemeCode)
            }
        }
        inFlightJobs[schemeCode as Int] = job
    }

    fun getDetailFlow(schemeCode: Int?): Flow<DetailUiState> =
        mutualFundRepository.observeFundByschemeCode(schemeCode)
            .map { entity -> entity.toDetailUiState() }

    private fun MutualFundDetail?.toDetailUiState(): DetailUiState = when {
        this == null              -> DetailUiState.Loading
        !detailsIsFetched           -> DetailUiState.Loading
        schemeCode != null && schemeName != null -> DetailUiState.Loaded(schemeCode, schemeName, latestNav)
        else                      -> DetailUiState.Error
    }
}