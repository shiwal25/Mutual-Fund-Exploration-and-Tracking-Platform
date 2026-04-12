package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mutualfundexplorationandtrackingplatform.ui.components.ExploreScreenFunds
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.CategoryUiState
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    onFundSelected: (Int?, String?) -> Unit,
    onViewAllClicked: (String) -> Unit
) {
    val indexFundsState by viewModel.indexFundsState.collectAsStateWithLifecycle()
    val bluechipFundsState by viewModel.bluechipFundsState.collectAsStateWithLifecycle()
    val taxFundsState by viewModel.taxFundsState.collectAsStateWithLifecycle()
    val largeCapFundsState by viewModel.largeCapFundsState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 4.dp)
    ) {
        OutlinedButton(
            onClick = onSearchClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Gray
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "Search funds...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        when (val state = indexFundsState) {
            is CategoryUiState.Success -> {
                if (state.funds.isNotEmpty()) {
                    ExploreScreenFunds(
                        funds = state.funds,
                        title = "INDEX FUNDS",
                        viewModel = viewModel,
                        onViewAllClick = { onViewAllClicked("INDEX FUNDS") },
                        onFundSelected = { code, name ->
                            onFundSelected(code, name)
                        },
                    )
                }
            }
            is CategoryUiState.Loading -> {
                CircularProgressIndicator()
            }
            is CategoryUiState.Empty -> {
                //Not shoiwng the category if empty
            }
            is CategoryUiState.Error -> {
                Text(text = "Error fetching Index Funds",color = Color.Red)
            }
        }


        when (val state = bluechipFundsState) {
            is CategoryUiState.Success -> {
                if (state.funds.isNotEmpty()) {
                    ExploreScreenFunds(
                        funds = state.funds,
                        title = "BLUECHIP FUNDS",
                        viewModel = viewModel,
                        onViewAllClick = { onViewAllClicked("BLUECHIP FUNDS") },
                        onFundSelected = { code, name ->
                            onFundSelected(code, name)
                        },
                    )
                }
            }
            is CategoryUiState.Loading -> {
                CircularProgressIndicator()
            }
            is CategoryUiState.Empty -> {
                //Not shoiwng the category if empty
            }
            is CategoryUiState.Error -> {
                Text(text = "Error fetching Blue Chip Funds",color = Color.Red)
            }
        }

        when (val state = taxFundsState) {
            is CategoryUiState.Success -> {
                if (state.funds.isNotEmpty()) {
                    ExploreScreenFunds(
                        funds = state.funds,
                        title = "TAX FUNDS",
                        viewModel = viewModel,
                        onViewAllClick = { onViewAllClicked("TAX SAVER FUNDS") },
                        onFundSelected = { code, name ->
                            onFundSelected(code, name)
                        },
                    )
                }
            }
            is CategoryUiState.Loading -> {
                CircularProgressIndicator()
            }
            is CategoryUiState.Empty -> {
                //Not shoiwng the category if empty
            }
            is CategoryUiState.Error -> {
                Text(text = "Error fetching Tax Saver Funds",color = Color.Red)
            }
        }

        when (val state = largeCapFundsState) {
            is CategoryUiState.Success -> {
                if (state.funds.isNotEmpty()) {
                    ExploreScreenFunds(
                        funds = state.funds,
                        title = "LARGE CAP FUNDS",
                        viewModel = viewModel,
                        onViewAllClick = { onViewAllClicked("LARGE CAP FUNDS") },
                        onFundSelected = { code, name ->
                            onFundSelected(code, name)
                        },
                    )
                }
            }
            is CategoryUiState.Loading -> {
                CircularProgressIndicator()
            }
            is CategoryUiState.Empty -> {
                //Not shoiwng the category if empty
            }
            is CategoryUiState.Error -> {
                Text(text = "Error fetching Large Cap Funds",color = Color.Red)
            }
        }
    }
}