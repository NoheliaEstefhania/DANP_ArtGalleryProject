package com.example.danp_artgallery.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Info


data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)
val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Search",
        icon = Icons.Default.Search,
        route = Screens.SearchScreen.name
    ),
    NavItem(
        label = "Map",
        icon = Icons.Default.LocationOn,
        route = Screens.MapScreen.name
    ),
    NavItem(
        label = "Info",
        icon = Icons.Default.Info,
        route = Screens.InfoScreen.name
    ),
    NavItem(
        label = "Beacon",
        icon = Icons.Default.Info,
        route = Screens.BeaconScreen.name
    ),
)

