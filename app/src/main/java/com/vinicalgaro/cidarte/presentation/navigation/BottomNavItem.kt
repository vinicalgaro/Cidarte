package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Library
import com.composables.icons.lucide.LibraryBig
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.SearchSlash

sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        route = AppRoutes.GRAPH_HOME,
        selectedIcon = Lucide.House,
        unselectedIcon = Lucide.House,
    )

    object Search : BottomNavItem(
        route = AppRoutes.GRAPH_SEARCH,
        selectedIcon = Lucide.SearchSlash,
        unselectedIcon = Lucide.Search
    )

    object Lib : BottomNavItem(
        route = AppRoutes.GRAPH_LIBRARY,
        selectedIcon = Lucide.LibraryBig,
        unselectedIcon = Lucide.Library
    )
}