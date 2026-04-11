package com.example.mutualfundexplorationandtrackingplatform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.ui.components.ViewAllButton
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.AnalysisScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.ExploreScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.ListScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.SearchScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.WatchListScreen

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
        containerColor = Color.White,
        topBar = {
            AppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() },
                onClick = {navController.navigate(MutualFundAppScreen.AllFunds.name)},
            )
        },
        bottomBar = {
            if(currentScreen == MutualFundAppScreen.Explore || currentScreen == MutualFundAppScreen.WatchList){
                BottomAppBar (
                    containerColor = Color.White,
                    contentPadding = PaddingValues(0.dp)
                ){
                    Column {
                        HorizontalDivider(thickness = 2.dp, color = Color.Black)
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(if (currentScreen == MutualFundAppScreen.Explore) Color.LightGray.copy(alpha = 0.3f) else Color.Transparent)
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
                    onClick = { navController.navigate(MutualFundAppScreen.Search.name) }
                )
            }

            composable (MutualFundAppScreen.AllFunds.name) {
                ListScreen(
//                    funds = List<MutualFund>,
                    {},
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.Analysis.name) {
                AnalysisScreen("","","","","",
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.Search.name) {
                SearchScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.WatchList.name) {
                WatchListScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.Funds.name) {
                ListScreen(
                    {},
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.Portfolio.name) {
                ListScreen(
                    {},
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
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
    Column{
        TopAppBar(
            title = { Text(currentScreen.name) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.White
            ),
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
            actions = {
                if (currentScreen == MutualFundAppScreen.Explore) {
                    ViewAllButton(onClick)
                }
            },
        )
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
    }
}