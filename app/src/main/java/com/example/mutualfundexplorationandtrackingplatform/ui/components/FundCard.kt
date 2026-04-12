package com.example.mutualfundexplorationandtrackingplatform.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.DetailUiState
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel
import kotlinx.coroutines.delay

@Composable
fun FundCard(
    fund: MutualFundDetail,
    viewModel: ExploreViewModel,
    onClick: (Int?, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val schemeCode = fund.schemeCode

    val detailState by viewModel
        .getDetailFlow(schemeCode)
        .collectAsStateWithLifecycle(initialValue = DetailUiState.Loading)

    LaunchedEffect(schemeCode) {
        delay(300L)
        viewModel.requestDetail(schemeCode)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable{onClick(fund.schemeCode, fund.schemeName)}
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .border(BorderStroke(2.dp, Color.Black), CircleShape),
                shape = CircleShape,
                color = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "AMC",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = fund.schemeName ?: "Unknown Fund",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "NAV",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            when (val state = detailState) {
                is DetailUiState.Loading -> {
                    ShimmerBox(
                        modifier = Modifier
                            .width(80.dp)
                            .height(24.dp)
                    )
                }
                is DetailUiState.Loaded -> {
                    Text(
                        text = "₹${state.latestNav}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )
                }
                is DetailUiState.Error -> {
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.LightGray.copy(alpha = 0.3f))
    )
}