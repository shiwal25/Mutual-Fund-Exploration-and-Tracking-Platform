package com.example.mutualfundexplorationandtrackingplatform

import android.R.attr.enabled
import android.text.style.BackgroundColorSpan
import androidx.annotation.StringRes
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mutualfundexplorationandtrackingplatform.data.MutualFund

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

@Composable
fun ListScreen(
//    funds: List<MutualFund>,
    onClick: () -> Unit,
    modifier:Modifier
){
    LazyColumn() {
//        items(items = funds) {
//            item -> listScreenItem(item, {})
//        }
    }
}

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

@Composable
fun AnalysisStatItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
    }
}

@Composable
fun SearchScreen(modifier: Modifier){

    /*TODO one composable for search results answers*/
}

@Composable
fun WatchListScreen(modifier: Modifier){
    /*TODO common UI for each new portfolio*/
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

@Composable
fun ViewAllButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "View All >"
        )
    }
}

@Composable
fun ExploreScreenFunds(
    funds: List<MutualFund>,
    title:String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(){
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = title,
            )
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Text(
                    text = "View All >"
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(items = funds){
                item -> FundCard(item, {})
            }
        }
    }
}

@Composable
fun FundCard(
    data: MutualFund,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
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
                text = "ICICI Sensex",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color.Black
            )

            Text(
                text = "NAV",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "119.30",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ExploreScreenFundsPreview(){
//    ExploreScreenFunds()
//}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ExploreFundsPreview() {
////    FundCard({}, Modifier)
//    listScreenItem("ICICI Prudential", "210.18", {})
//}


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