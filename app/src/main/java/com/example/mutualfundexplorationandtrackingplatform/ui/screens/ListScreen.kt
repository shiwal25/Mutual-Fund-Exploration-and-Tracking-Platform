package com.example.mutualfundexplorationandtrackingplatform.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mutualfundexplorationandtrackingplatform.ui.components.listScreenItem
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel

@Composable
fun ListScreen(
    viewModel: ExploreViewModel,
    onClick: () -> Unit,
    modifier:Modifier
){
    val lazyPagingItems = viewModel.mutalFundPagingFlow.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count    = lazyPagingItems.itemCount,
            key      = lazyPagingItems.itemKey { it.schemeCode!! }
        ) { index ->
            val item = lazyPagingItems[index] ?: return@items
            listScreenItem(
                schemeCode = item.schemeCode,
                schemeName = item.schemeName,
                viewModel = viewModel,
                {}
            )
        }

        item {
            when (val append = lazyPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    Box(
                        modifier          = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment  = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                }
                is LoadState.Error -> {
                    Box(
                        modifier         = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Failed to load more", color = MaterialTheme.colorScheme.error)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { lazyPagingItems.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    when (val refresh = lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            CircularProgressIndicator(modifier = Modifier)
        }
        is LoadState.Error -> {
            Column(
                modifier          = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text  = refresh.error.localizedMessage ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(12.dp))
                Button(onClick = { lazyPagingItems.refresh() }) {
                    Text("Retry")
                }
            }
        }
        else -> Unit
    }
    }