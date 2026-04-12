package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository
import com.example.mutualfundexplorationandtrackingplatform.ui.components.DetailFetchViewModel
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.DetailUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class SearchViewModel(
    private val mutualFundRepository: MutualFundRepository
) : ViewModel(), DetailFetchViewModel {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<MutualFundDTO>>(emptyList())
    val searchResults: StateFlow<List<MutualFundDTO>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var searchJob: Job? = null

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _error.value = null
            return
        }

        searchJob = viewModelScope.launch {
            delay(300L)
            performSearch(query)
        }
    }

    private suspend fun performSearch(query: String) {
        _isLoading.value = true
        _error.value = null
//show loader when isLaoding is true
        try {
            val results = mutualFundRepository.searchFunds(query)
            _searchResults.value = results
        } catch (e: Exception) {
            _error.value = e.message ?: "Search failed"
            _searchResults.value = emptyList()
        } finally {
            _isLoading.value = false
        }
    }

    private val fetchNavJob = ConcurrentHashMap<Int, Job>()

    override fun requestDetail(schemeCode: Int?) {
        if (fetchNavJob.containsKey(schemeCode)) return

        val job = viewModelScope.launch {
            try {
                mutualFundRepository.fetchAndCacheDetails(schemeCode)
            } finally {
                fetchNavJob.remove(schemeCode)
            }
        }
        fetchNavJob[schemeCode as Int] = job
    }

    override fun getDetailFlow(schemeCode: Int?): Flow<DetailUiState> {
        return mutualFundRepository.observeFundByschemeCode(schemeCode)
            .map { fund -> fund.toDetailUiState() }
            .catch { emit(DetailUiState.Error) }
    }

    private fun MutualFundDetail?.toDetailUiState(): DetailUiState {
        return when {
            this == null -> DetailUiState.Loading
            !detailsIsFetched -> DetailUiState.Loading
            schemeCode != null && schemeName != null -> {
                DetailUiState.Loaded(schemeCode, schemeName, latestNav, schemeCategory)
            }
            else -> DetailUiState.Error
        }
    }
}
