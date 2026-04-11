package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExploreScreen(modifier:Modifier,
                  onClick: () -> Unit
) {
    Column(modifier.padding(4.dp)) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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

        /*TODO now common composables for each and every funds*/
//        if(indexFunds != null){
//            ExploreScreenFunds(indexFunds, INDEX FUNDS, {})
//        }
//        if(BluechipFunds != null){
//            ExploreScreenFunds(indexFunds, BLUECHIP FUNDS, {})
//        }if(taxFunds != null){
//        ExploreScreenFunds(indexFunds, TAX FUNDS, {})
    }
}