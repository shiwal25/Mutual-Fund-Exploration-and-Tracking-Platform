package com.example.mutualfundexplorationandtrackingplatform

import android.R.attr.type
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.ui.components.ViewAllButton
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.AnalysisScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.CategoryListScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.ExploreScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.ListScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.SearchScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.screens.WatchListScreen
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ExploreViewModel
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.ViewModelFactory
import com.example.mutualfundexplorationandtrackingplatform.ui.viewmodels.WatchlistViewModel

enum class MutualFundAppScreen (val route: String){
    Explore("explore"),
    AllFunds("allFunds"),
    Analysis("analysis"),
    Search("search"),
    WatchList("watchlist"),
    Funds("funds"),
    Portfolio("portfolio"),
    CategoryAllFunds("categoryAllFunds")
}


@Composable
fun MutualFundApp(
    navController: NavHostController = rememberNavController(),
){

    var showBottomSheet by remember { mutableStateOf(false) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: MutualFundAppScreen.Explore.name
    val baseRoute = currentRoute.substringBefore("/")
    val currentScreen = try {
        MutualFundAppScreen.valueOf(baseRoute)
    } catch (e: IllegalArgumentException) {
        MutualFundAppScreen.Explore
    }
    val appBarTitle = if (currentScreen == MutualFundAppScreen.CategoryAllFunds) {
        backStackEntry?.arguments?.getString("categoryName") ?: "All Funds"
    } else {
        currentScreen.name
    }
    val context = LocalContext.current
    val app = context.applicationContext as MutualFundApplication
    val mutualFundRepository = app.container.mutualFundRepository
    val watchListDao = app.container.watchListDao

    val exploreViewModel: ExploreViewModel = viewModel(
        factory = ViewModelFactory(mutualFundRepository, watchListDao)
    )

    val watchlistViewModel: WatchlistViewModel = viewModel(
        factory = ViewModelFactory(mutualFundRepository, watchListDao)
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppBar(
                title = appBarTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() },
                onClick = {navController.navigate(MutualFundAppScreen.AllFunds.name)},
                onBookmarkClick = {
                    if (currentScreen == MutualFundAppScreen.Analysis) {
                        showBottomSheet = true
                    }
                }
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
                                Text(text = "Explore", color = Color.Black)
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
                                Text(text = "Watchlist", color = Color.Black)
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
                    viewModel = exploreViewModel,
                    onSearchClick = { navController.navigate(MutualFundAppScreen.Search.name) },
                    onFundSelected = { code, name ->
                        exploreViewModel.selectFund(code, name)
                        navController.navigate(MutualFundAppScreen.Analysis.name)
                    },
                    onViewAllClicked = { categoryName ->
                        navController.navigate("${MutualFundAppScreen.CategoryAllFunds.name}/$categoryName")
                    }
                )
            }

            composable (
                route = "${MutualFundAppScreen.CategoryAllFunds.name}/{categoryName}",
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ){ backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""

                CategoryListScreen(
                    categoryName = categoryName,
                    viewModel = exploreViewModel,
                    modifier = Modifier.padding(innerPadding),
                    onClick = { code, name ->
                        exploreViewModel.selectFund(code, name)
                        navController.navigate(MutualFundAppScreen.Analysis.name)
                    }
                )
            }

            composable (MutualFundAppScreen.AllFunds.name) {
                ListScreen(
                    viewModel = exploreViewModel,
                    { code, name ->
                        exploreViewModel.selectFund(code, name)
                        navController.navigate(MutualFundAppScreen.Analysis.name)
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.Analysis.name) {
                AnalysisScreen(
                    watchlistViewModel = watchlistViewModel,
                    viewModel= exploreViewModel,
                    modifier = Modifier.padding(innerPadding),
                    showBottomSheet = showBottomSheet,
                    onDismissBottomSheet = { showBottomSheet = false }
                )
            }

            composable (MutualFundAppScreen.Search.name) {
                SearchScreen(
                    modifier = Modifier.padding(innerPadding),
                    onClick = {}
                    ,
                )
            }

            composable (MutualFundAppScreen.WatchList.name) {
                WatchListScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }

            composable (MutualFundAppScreen.Funds.name) {
//                ListScreen(
//                    {},
//                    modifier = Modifier.padding(innerPadding),
//                )
            }

            composable (MutualFundAppScreen.Portfolio.name) {
//                ListScreen(
//                    {},
//                    modifier = Modifier.padding(innerPadding),
//                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title:String,
    currentScreen: MutualFundAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit = {}
) {
    Column{
        TopAppBar(
            title = { Text(title, color = Color.Black) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
                navigationIconContentColor = Color.Black,
                actionIconContentColor = Color.Black
            ),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            },
            actions = {
                if (currentScreen == MutualFundAppScreen.Explore) {
                    ViewAllButton(onClick)
                }

                if (currentScreen == MutualFundAppScreen.Analysis) {
                    IconButton(onClick = onBookmarkClick) {
                        Icon(
                            imageVector = Icons.Filled.BookmarkBorder,
                            contentDescription = "Bookmark Mutual Fund"
                        )
                    }
                }
            },
        )
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
    }
}