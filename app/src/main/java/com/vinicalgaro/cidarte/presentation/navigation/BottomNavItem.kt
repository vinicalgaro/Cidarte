package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SavedSearch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object Home :
        BottomNavItem(Destinations.HOME_ROUTE, Icons.Filled.Home, Icons.Outlined.Home)
    object Search :
        BottomNavItem(Destinations.SEARCH_ROUTE, Icons.Filled.SavedSearch, Icons.Outlined.Search)
    object Settings :
        BottomNavItem(Destinations.SETTINGS_ROUTE, Icons.Filled.Settings, Icons.Outlined.Settings)
}