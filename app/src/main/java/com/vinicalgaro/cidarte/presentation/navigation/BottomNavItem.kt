package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SavedSearch
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        route = AppRoutes.GRAPH_HOME,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    object Search : BottomNavItem(
        route = AppRoutes.GRAPH_SEARCH,
        selectedIcon = Icons.Filled.SavedSearch,
        unselectedIcon = Icons.Outlined.Search
    )

    object About : BottomNavItem(
        route = AppRoutes.GRAPH_ABOUT,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info
    )
}