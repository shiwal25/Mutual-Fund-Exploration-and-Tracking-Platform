package com.example.mutualfundexplorationandtrackingplatform

import android.R.attr.enabled
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class MutualFundAppScreen (val route: String){
    Explore("explore"),
    AllFunds("allFunds"),
    Analysis("analysis"),
    Search("search"),
    WatchList("watchlist"),
    Funds("funds"),
    Portfolio("portfolio")
}


@Composable
fun MutualFundApp(
    navController: NavHostController = rememberNavController(),
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MutualFundAppScreen.valueOf(
        backStackEntry?.destination?.route ?: MutualFundAppScreen.Explore.name
    )

    Scaffold(
        topBar = {
            AppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() },
                onClick = {navController.navigate(MutualFundAppScreen.AllFunds.name)}
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(if (currentScreen == MutualFundAppScreen.Explore) Color.LightGray.copy(alpha = 0.8f) else Color.Transparent)
                            .clickable (enabled = currentScreen != MutualFundAppScreen.Explore) {
                                navController.navigate(MutualFundAppScreen.Explore.name) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }},
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Explore")
                    }


                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(if (currentScreen == MutualFundAppScreen.WatchList) Color.LightGray.copy(alpha = 0.8f) else Color.Transparent)
                            .clickable (enabled = currentScreen != MutualFundAppScreen.WatchList) {
                                navController.navigate(MutualFundAppScreen.WatchList.name) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }},
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Watchlist")
                    }
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MutualFundAppScreen.Explore.name
        ) {
            composable (MutualFundAppScreen.Explore.name) {
                ExploreScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}


@Composable
fun ExploreScreen(modifier: Modifier) {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: MutualFundAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(currentScreen.name) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },

    )
}