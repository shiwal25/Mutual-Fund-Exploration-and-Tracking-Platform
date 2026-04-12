package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mutualfundexplorationandtrackingplatform.ui.components.AnalysisStatItem
import com.example.mutualfundexplorationandtrackingplatform.ui.components.NavLineChart
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.DetailUiState
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    viewModel: ExploreViewModel,
    watchlistViewModel: WatchlistViewModel,
    modifier: Modifier = Modifier,
    showBottomSheet:Boolean,
    onDismissBottomSheet: () -> Unit
) {

    var selectedPeriod by remember { mutableStateOf("6M") }

    val selectedFund by viewModel.selectedFund.collectAsStateWithLifecycle()
    val schemeCode = selectedFund?.schemeCode

    val detailState by viewModel
        .getDetailFlow(schemeCode)
        .collectAsStateWithLifecycle(initialValue = DetailUiState.Loading)

    LaunchedEffect(schemeCode) {
        viewModel.requestDetail(schemeCode)
    }

    LaunchedEffect(schemeCode, selectedPeriod) {
        viewModel.fetchNavDataForGraph(schemeCode?.toString(), selectedPeriod)
    }

    val fundName = when (val state = detailState) {
        is DetailUiState.Loaded -> state.schemeName ?: "Unknown Fund"
        else -> selectedFund?.schemeName ?: "Unknown Fund"
    }

    val category = when (val state = detailState) {
        is DetailUiState.Loaded -> state.schemeCategory ?: "Unknown Category"
        else -> "Loading..."
    }

    val navValue = when (val state = detailState) {
        is DetailUiState.Loaded -> state.latestNav ?: "-"
        is DetailUiState.Loading -> "-"
        is DetailUiState.Error -> "-"
    }

    val navData by viewModel.navData.collectAsStateWithLifecycle()
    val isLoadingGraph by viewModel.isLoadingGraph.collectAsStateWithLifecycle()

    val growthPercentage = remember(navData) {
        if (navData.size >= 2) {
            val firstNav = navData.lastOrNull()?.nav?.toFloatOrNull() ?: 0f
            val lastNav = navData.firstOrNull()?.nav?.toFloatOrNull() ?: 0f
            if (firstNav > 0) {
                val growth = ((lastNav - firstNav) / firstNav) * 100
                String.format("%.2f%%", growth)
            } else "0%"
        } else "0%"
    }

    val (fundType, typeColor) = remember(navData) {
        if (navData.size >= 2) {
            val firstNav = navData.lastOrNull()?.nav?.toDoubleOrNull() ?: 0.0
            val lastNav = navData.firstOrNull()?.nav?.toDoubleOrNull() ?: 0.0

            if (firstNav > 0) {
                val growth = ((lastNav - firstNav) / firstNav) * 100

                when {
                    growth > 0.1 -> Pair("Growth", Color.Green)
                    growth < -0.1 -> Pair("Decline", Color.Red)
                    else -> Pair("Stable", Color.Gray)
                }
            } else {
                Pair("Stable", Color.Gray)
            }
        } else {
            Pair("-", Color.Gray)
        }
    }

    val description = "Fund details not available."

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ){

        Column(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
        ) {
            Text(
                text = fundName,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Text(
                text = "Category: $category",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "NAV $navValue",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                val (growthArrow, growthColor) = remember(navData) {
                    if (navData.size >= 2) {
                        val firstNav = navData.lastOrNull()?.nav?.toFloatOrNull() ?: 0f
                        val lastNav = navData.firstOrNull()?.nav?.toFloatOrNull() ?: 0f

                        if (firstNav > 0) {
                            val growth = ((lastNav - firstNav) / firstNav) * 100

                            when {
                                growth > 0.1 -> Pair("↑", Color.Green)
                                growth < -0.1 -> Pair("↓", Color.Red)
                                else -> Pair("→", Color.Gray)
                            }
                        } else {
                            Pair("→", Color.Gray)
                        }
                    } else {
                        Pair("→", Color.Gray)
                    }
                }

                Text(
                    text = "$growthArrow $growthPercentage",
                    style = MaterialTheme.typography.bodyLarge,
                    color = growthColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoadingGraph) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                NavLineChart(
                    navData = navData,
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(4.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf("6M", "1Y", "ALL").forEach { period ->
                    val selected = period == selectedPeriod
                    Text(
                        text = period,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                selectedPeriod = period
                            },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            textDecoration = if (selected) TextDecoration.Underline else TextDecoration.None
                        ),
                        color = if (selected) Color.Black else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp,
                color = Color.Black
            )
        }

        Column {
            HorizontalDivider(thickness = 1.dp, color = Color.Black)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = fundType,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = typeColor
                    )
                }

                VerticalDivider(modifier = Modifier.height(40.dp), color = Color.Black)
                AnalysisStatItem("Size", "-", Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(40.dp), color = Color.Black)
                AnalysisStatItem("NAV", navValue, Modifier.weight(1f))
            }
        }
    }

    if (showBottomSheet) {
        var newPortfolioName by remember { mutableStateOf("") }

        val watchlists by watchlistViewModel.allWatchlists.collectAsStateWithLifecycle()

        val watchlistsContainingFund by remember(schemeCode) {
            watchlistViewModel.getWatchlistsForFund(schemeCode)
        }.collectAsStateWithLifecycle(initialValue = emptyList())

        androidx.compose.material3.ModalBottomSheet(
            onDismissRequest = onDismissBottomSheet,
            containerColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add to Portfolio",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier.padding(4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newPortfolioName,
                        onValueChange = { newPortfolioName = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        placeholder = { Text("New Portfolio Name...") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Black
                        )                    )

                    OutlinedButton(
                        onClick = {
                            if (newPortfolioName.isNotBlank()) {
                                watchlistViewModel.addWatchlist(newPortfolioName)
                                newPortfolioName = ""
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Add")
                    }
                }

                HorizontalDivider(thickness = 2.dp, color = Color.Black, modifier = Modifier.padding(vertical = 8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    items(watchlists, key = { it.watchListId }) { watchlist ->
                        val isChecked = watchlistsContainingFund.contains(watchlist.watchListId)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (schemeCode != null) {
                                        watchlistViewModel.toggleFundInWatchlist(
                                            watchlistId = watchlist.watchListId,
                                            schemeCode = schemeCode,
                                            isChecked = !isChecked
                                        )
                                    }
                                }
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    if (schemeCode != null) {
                                        watchlistViewModel.toggleFundInWatchlist(
                                            watchlistId = watchlist.watchListId,
                                            schemeCode = schemeCode,
                                            isChecked = checked
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = watchlist.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
