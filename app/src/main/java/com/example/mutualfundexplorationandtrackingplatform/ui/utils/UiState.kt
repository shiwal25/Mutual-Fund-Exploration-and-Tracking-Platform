package com.example.mutualfundexplorationandtrackingplatform.ui.utils

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Loaded(val schemeCode: Int?, val schemeName: String?, val latestNav: String?, val schemeCategory: String?) : DetailUiState
    data object Error : DetailUiState
}