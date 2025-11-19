package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector) {
    object Home : BottomNavItem(Destinations.HOME_ROUTE, Icons.Default.Home)
    object Search : BottomNavItem(Destinations.SEARCH_ROUTE, Icons.Default.Search)
    object Settings : BottomNavItem(Destinations.SETTINGS_ROUTE, Icons.Default.Settings)
}