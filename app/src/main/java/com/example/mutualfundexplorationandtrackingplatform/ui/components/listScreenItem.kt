package com.example.mutualfundexplorationandtrackingplatform.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund

@Composable
fun listScreenItem(
    data: MutualFund,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
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
//            Column {
//                Text(
//                    text = data.name,
//                    style = MaterialTheme.typography.titleMedium.copy(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp
//                    ),
//                    color = Color.Black
//                )
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = "NAV: ",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray
//                    )
//                    Text(
//                        text = data.name,
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontWeight = FontWeight.SemiBold
//                        ),
//                        color = Color.Black
//                    )
//                }
//            }
        }
    }
}