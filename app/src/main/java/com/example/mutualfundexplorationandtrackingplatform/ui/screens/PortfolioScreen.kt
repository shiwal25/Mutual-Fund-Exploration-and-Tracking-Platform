package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.WatchlistViewModel

@Composable
fun PortfolioScreen(
    watchlistId: Long,
    watchlistViewModel: WatchlistViewModel,
    modifier: Modifier = Modifier,
    onExploreFundsClick: () -> Unit,
    onFundClick: (Int, String) -> Unit
) {
    val funds by watchlistViewModel.getFundsInWatchlist(watchlistId).collectAsStateWithLifecycle(initialValue = emptyList())

    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        if (funds.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Inventory,
                    contentDescription = "Empty Portfolio",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "No funds added yet.",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Explore the market to save funds into\nthis portfolio.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                    color = Color.DarkGray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedButton(
                    onClick = onExploreFundsClick,
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "Explore Funds",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(funds, key = { it.schemeCode ?: 0 }) { fund ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onFundClick(
                                    fund.schemeCode ?: 0,
                                    fund.schemeName ?: "Unknown Fund"
                                )
                            },
                        border = BorderStroke(2.dp, Color.Black),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                fund.schemeName?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.Black
                                    )
                                }
                                Text(
                                    text = fund.fundHouse ?: "Unknown Fund House",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "View Fund",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}