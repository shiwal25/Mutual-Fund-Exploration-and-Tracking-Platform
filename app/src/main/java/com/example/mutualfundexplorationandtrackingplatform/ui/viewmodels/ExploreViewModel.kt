package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.DetailUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

sealed class CategoryUiState {
    object Loading : CategoryUiState()
    data class Success(val funds: List<MutualFundDetail>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
    object Empty : CategoryUiState()
}

class ExploreViewModel(
    private val mutualFundRepository: MutualFundRepository
): ViewModel() {

    val mutalFundPagingFlow = mutualFundRepository.mutalFundPagingFlow
        .cachedIn(viewModelScope)

    private val _indexFundsState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val indexFundsState: StateFlow<CategoryUiState> = _indexFundsState.asStateFlow()

    private val _bluechipFundsState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val bluechipFundsState: StateFlow<CategoryUiState> = _bluechipFundsState.asStateFlow()

    private val _taxFundsState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val taxFundsState: StateFlow<CategoryUiState> = _taxFundsState.asStateFlow()

    private val _largeCapFundsState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val largeCapFundsState: StateFlow<CategoryUiState> = _largeCapFundsState.asStateFlow()

    private val _selectedFund = MutableStateFlow<SelectedFund?>(null)
    val selectedFund: StateFlow<SelectedFund?> = _selectedFund.asStateFlow()

    fun selectFund(schemeCode: Int?, schemeName: String?) {
        _selectedFund.value = SelectedFund(schemeCode, schemeName)
    }

    private fun loadAllCategories() {
        loadCategory("Index Funds", _indexFundsState)
        loadCategory("BlueChip", _bluechipFundsState)
        loadCategory("Tax Savers (ELSS)", _taxFundsState)
        loadCategory("Large Cap", _largeCapFundsState)
    }

    init {
        Log.d("ExploreViewModel", "ViewModel initialized, starting loadAllCategories()")
        try {
            Log.d("ExploreViewModel", "ViewModel initialized")
            loadAllCategories()
        } catch (e: Exception) {
            Log.e("ExploreViewModel", "Error in init: ${e.message}", e)
        }
    }

    private fun loadCategory(category: String, stateFlow: MutableStateFlow<CategoryUiState>) {
        Log.d("ExploreViewModel", "loadCategory called for: $category")

        viewModelScope.launch {
            Log.d("ExploreViewModel", "Starting DB observation for: $category")
            mutualFundRepository.observeCategoryFunds(category)
                .map { funds ->
                    Log.d("ExploreViewModel", "DB returned ${funds.size} funds for $category")
                    if (funds.isEmpty()) {
                        CategoryUiState.Empty
                    } else {
                        Log.d("EVM", "loadCategory: sucesssssssssss")
                        CategoryUiState.Success(funds.take(4)) // Max 4 items
                    }
                }
                .catch { e ->
                    Log.e("ExploreViewModel", "Error observing $category: ${e.message}", e)
                    emit(CategoryUiState.Error(e.message ?: "Unknown error"))
                }
                .collect { state ->
                    Log.d("ExploreViewModel", "Emitting state for $category: $state")
                    stateFlow.value = state
                }
        }

            viewModelScope.launch {
                try {
                    val result = mutualFundRepository.fetchAndCacheCategoryFunds(category)

                    result.onSuccess { funds ->
                        stateFlow.value = if (funds.isEmpty()) {
                            CategoryUiState.Empty
                        } else {
                            CategoryUiState.Success(funds)
                        }
                    }.onFailure { e ->
                        stateFlow.value = CategoryUiState.Error(e.message ?: "Failed to load funds")
                    }
                } catch (e: Exception) {
                    stateFlow.value = CategoryUiState.Error(e.message ?: "Unknown error")
                }
            }
        }

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

    fun getDetailFlow(schemeCode: Int?): Flow<DetailUiState> {
        return mutualFundRepository.observeFundByschemeCode(schemeCode)
            .map { fund -> fund.toDetailUiState() }
            .catch { e ->
                emit(DetailUiState.Error)
            }
    }

    fun refresh() {
        loadAllCategories()
    }

    private fun MutualFundDetail?.toDetailUiState(): DetailUiState {
        Log.d("ViewModel", "toDetailUiState: this=$this, detailsIsFetched=${this?.detailsIsFetched}, latestNav=${this?.latestNav}")

        return when {
            this == null -> DetailUiState.Loading
            !detailsIsFetched -> DetailUiState.Loading
            schemeCode != null && schemeName != null -> {
                Log.d("ViewModel", "Returning Loaded state with nav=$latestNav")
                DetailUiState.Loaded(schemeCode, schemeName, latestNav, schemeCategory)
            }
            else -> DetailUiState.Error
        }
    }
}

data class SelectedFund(
    val schemeCode: Int?,
    val schemeName: String?,
    val schemeCategory: String? = null,
    val description: String? = null,
    val growthPercentage: String? = null
)