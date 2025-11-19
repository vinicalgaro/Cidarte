package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinicalgaro.cidarte.presentation.components.CidarteBottomNavigationBar
import com.vinicalgaro.cidarte.presentation.screens.home.HomeScreen
import com.vinicalgaro.cidarte.presentation.screens.search.SearchScreen
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionGridScreen
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionType
import com.vinicalgaro.cidarte.presentation.screens.settings.SettingsScreen

object Destinations {
    const val HOME_ROUTE = "home"
    const val GRID_ROUTE_BASE = "grid"
    const val GRID_ROUTE = "$GRID_ROUTE_BASE/{${SectionType.SECTION_TYPE_KEY}}"
    const val SEARCH_ROUTE = "search"
    const val SETTINGS_ROUTE = "settings"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Destinations.HOME_ROUTE,
        Destinations.SEARCH_ROUTE,
        Destinations.SETTINGS_ROUTE
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                CidarteBottomNavigationBar(navController = navController)
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.HOME_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destinations.HOME_ROUTE) {
                HomeScreen(
                    onSeeMoreClick = { sectionType ->
                        navController.navigate(
                            route = "${Destinations.GRID_ROUTE_BASE}/$sectionType"
                        )
                    }
                )
            }
            composable(Destinations.SEARCH_ROUTE) {
                SearchScreen()
            }
            composable(Destinations.SETTINGS_ROUTE) {
                SettingsScreen()
            }
            composable(
                Destinations.GRID_ROUTE,
                arguments = listOf(
                    navArgument(SectionType.SECTION_TYPE_KEY) {
                        type = NavType.StringType
                    }
                )
            ) {
                SectionGridScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}