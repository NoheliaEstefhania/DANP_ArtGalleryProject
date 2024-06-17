package com.example.danp_artgallery.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.danp_artgallery.screens.home.HomeScreen
import com.example.danp_artgallery.screens.SearchScreen
import com.example.danp_artgallery.screens.MapScreen
import com.example.danp_artgallery.screens.InfoScreen
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBckStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBckStackEntry?.destination
                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
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
                HomeScreen()
            }

            composable(route = Screens.SearchScreen.name) {
                SearchScreen()
            }


            composable(route = Screens.MapScreen.name) {
                MapScreen()
            }

            composable(route = Screens.InfoScreen.name) {
                InfoScreen()
            }
        }
    }
}