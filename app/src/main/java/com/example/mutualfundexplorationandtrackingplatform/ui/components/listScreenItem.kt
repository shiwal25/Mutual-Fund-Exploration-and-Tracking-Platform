package com.example.mutualfundexplorationandtrackingplatform.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mutualfundexplorationandtrackingplatform.ui.utils.DetailUiState
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel
import kotlinx.coroutines.delay

@Composable
fun listScreenItem(
    schemeCode: Int?,
    schemeName: String?,
    viewModel: ExploreViewModel,
    onClick: (Int?, String?) -> Unit,
    modifier: Modifier = Modifier
){
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
            .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(4.dp))
            .clickable {
                onClick(schemeCode, schemeName)
            }
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .size(48.dp)
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
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                if (schemeName != null) {
                    Text(
                        text = schemeName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "NAV: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    when (val state = detailState) {
                        is DetailUiState.Loading -> {
                            ShimmerBox(width = 160.dp, height = 14.dp)
                        }
                        is DetailUiState.Loaded -> {
                            state.latestNav?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme
                                        .typography
                                        .bodyLarge
                                        .copy(
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                    color = Color.Black
                                )
                            }
                        }
                        is DetailUiState.Error -> {
                            Text(
                                text     = "-",
                                fontSize = 14.sp,
                                color    = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                }
            }
        }
    }
}