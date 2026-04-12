package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mutualfundexplorationandtrackingplatform.ui.components.ListScreenItem
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.CategoryUiState
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel

@Composable
fun CategoryListScreen(
    categoryName: String,
    viewModel: ExploreViewModel,
    onClick: (Int?, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val indexFundsState by viewModel.indexFundsState.collectAsStateWithLifecycle()
    val bluechipFundsState by viewModel.bluechipFundsState.collectAsStateWithLifecycle()
    val taxFundsState by viewModel.taxFundsState.collectAsStateWithLifecycle()
    val largeCapFundsState by viewModel.largeCapFundsState.collectAsStateWithLifecycle()

    val fundsList = when (categoryName) {
        "INDEX FUNDS" -> (indexFundsState as? CategoryUiState.Success)?.funds ?: emptyList()
        "BLUECHIP FUNDS" -> (bluechipFundsState as? CategoryUiState.Success)?.funds ?: emptyList()
        "TAX FUNDS" -> (taxFundsState as? CategoryUiState.Success)?.funds ?: emptyList()
        "LARGE CAP FUNDS" -> (largeCapFundsState as? CategoryUiState.Success)?.funds ?: emptyList()
        else -> emptyList()
    }

    if (fundsList.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No funds found for $categoryName", color = Color.Red)
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(
                items = fundsList,
                key = { fund -> fund.schemeCode ?: fund.hashCode() }
            ) { fund ->
                ListScreenItem(
                    schemeCode = fund.schemeCode,
                    schemeName = fund.schemeName,
                    viewModel = viewModel,
                    onClick = { code, name ->
                        onClick(code, name)
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}