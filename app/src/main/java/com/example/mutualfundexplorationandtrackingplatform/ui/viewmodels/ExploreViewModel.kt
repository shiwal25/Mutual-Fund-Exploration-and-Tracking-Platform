package com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import perfetto.protos.UiState

class ExploreViewModel(    private val mutualFundRepository: MutualFundRepository
): ViewModel() {

//    private val _indexFunds = MutableStateFlow<UiState<List<MutualFund>>>(UiState.Loading)
//    val indexFunds: StateFlow<UiState<List<MutualFund>>> = _indexFunds.asStateFlow()
//
//    private val _bluechipFunds = MutableStateFlow<UiState<List<MutualFund>>>(UiState.Loading)
//    val bluechipFunds: StateFlow<UiState<List<MutualFund>>> = _bluechipFunds.asStateFlow()
//
//    private val _taxFunds = MutableStateFlow<UiState<List<MutualFund>>>(UiState.Loading)
//    val taxFunds: StateFlow<UiState<List<MutualFund>>> = _taxFunds.asStateFlow()
//
//    private val _largeCapFunds = MutableStateFlow<UiState<List<MutualFund>>>(UiState.Loading)
//    val largeCapFunds: StateFlow<UiState<List<MutualFund>>> = _largeCapFunds.asStateFlow()


}