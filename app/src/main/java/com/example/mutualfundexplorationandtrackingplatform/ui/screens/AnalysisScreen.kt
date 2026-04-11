package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mutualfundexplorationandtrackingplatform.ui.components.AnalysisStatItem

@Composable
fun AnalysisScreen(
    fundName: String,
    category: String,
    navValue: String,
    growthPercentage: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
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
            Text(
                text = "↑ $growthPercentage",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF4CAF50)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Graph Placeholder", color = Color.LightGray)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf("6M", "1Y", "ALL").forEach { period ->
                Text(
                    text = period,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (period == "1Y") FontWeight.Bold else FontWeight.Normal,
                        textDecoration = if (period == "1Y") TextDecoration.Underline else null
                    ),
                    color = if (period == "1Y") Color.Black else Color.Gray
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

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Black)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnalysisStatItem("Type", "Growth", Modifier.weight(1f))
            VerticalDivider(modifier = Modifier.height(40.dp), color = Color.Black)
            AnalysisStatItem("Size", "₹35k Cr", Modifier.weight(1f))
            VerticalDivider(modifier = Modifier.height(40.dp), color = Color.Black)
            AnalysisStatItem("NAV", navValue, Modifier.weight(1f))
        }
    }
}