package com.example.danp_artgallery.navigation

import ExpositionLargeDetailScreen
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.beacon.BeaconScreen
import com.example.danp_artgallery.data.viewmodel.ExpositionViewModel
import com.example.danp_artgallery.data.viewmodel.PaintingViewModel
import com.example.danp_artgallery.home.Expositions.ExpositionListScreen
import com.example.danp_artgallery.home.HomeScreen
import com.example.danp_artgallery.info.InfoScreen
import com.example.danp_artgallery.map.CityMapScreen
import com.example.danp_artgallery.screens.views.PaintDetailScreen
import com.example.danp_artgallery.screens.views.PaintDetailView
import com.example.danp_artgallery.screens.views.PaintingDetailScreen
import com.example.danp_artgallery.screens.views.PaintingListScreen
import com.example.danp_artgallery.search.SearchScreen
import com.example.danp_artgallery.settings.SettingScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(context: Context, lifecycleOwner: ComponentActivity) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == navItem.route
                        } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )

                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name) {
                HomeScreen(
                    navigateToExpositionDetail = { expositionTitle ->
                        navController.navigate("${
                                Screens.ExpositionDetailScreen.name
                            }/$expositionTitle"
                        )
                    }
                , navController = navController)
            }
            composable(route = Screens.SearchScreen.name) {
                SearchScreen(navController = navController, )
            }
            composable(route = Screens.MapScreen.name) {
                CityMapScreen(
                    context,
                    navController = navController,
                    navigateToExpositionDetail = { expositionTitle ->
                    navController.navigate("${
                        Screens.ExpositionDetailScreen.name
                    }" +
                            "/$expositionTitle"
                    )
                    }
                )
            }
            composable(route = Screens.InfoScreen.name) {
                InfoScreen(navController = navController)
            }
            composable(route = Screens.SettingScreen.name) {
                SettingScreen(navController = navController)
            }

            composable(route = Screens.BeaconScreen.name){
                BeaconScreen(context, lifecycleOwner, navController)
            }
            composable(route = Screens.PaintingListScreen.name) {
                Log.d("AppNavigation", "Navigating to PaintingListScreen")
                PaintingListScreen(
                    onPaintingClick = { paintingId ->
                        navController.navigate("${Screens.PaintingDetailScreen.name}/$paintingId")
                    }
                )
            }
            composable(route = Screens.ExpositionListScreen.name) {
                ExpositionListScreen(
                    navigateToExpositionDetail = { expositionId ->
                        navController.navigate("${Screens.ExpositionDetailScreen.name}/$expositionId")
                    }
                    , navController = navController
                )
            }
            composable(route = "${Screens.ExpositionDetailScreen.name}/{expositionId}") { backStackEntry ->
                Log.d("ExpositionDetailScreen", "ExpositionDetailScreen: ${backStackEntry.arguments?.getString("expositionId")}")
                val expositionId = backStackEntry.arguments?.getString("expositionId")?.toIntOrNull()
                if (expositionId  != null) {
                    val viewModel: ExpositionViewModel = viewModel()
                    ExpositionLargeDetailScreen(viewModel = viewModel, expositionId = expositionId, navController = navController)
                }
            }
            composable(route = "${Screens.PaintingDetailScreen.name}/{paintingId}") { backStackEntry ->
                Log.d("PaintingDetailScreen", "PaintingDetailScreen: ${backStackEntry.arguments?.getString("paintingId")}")
                //Log.d("AppNavigation", "Navigating to PaintingDetailScreen with ID: $paintingId")
                val paintingId = backStackEntry.arguments?.getString("paintingId")?.toIntOrNull()
                if (paintingId != null) {
                    val viewModel: PaintingViewModel = viewModel()
                    PaintingDetailScreen(viewModel = viewModel, paintingId = paintingId, navController = navController)
                }
            }
        }
    }
}
