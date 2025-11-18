package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinicalgaro.cidarte.presentation.screens.home.HomeScreen
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionGridScreen
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionType

object Destinations {
    const val HOME_ROUTE = "home"
    const val GRID_ROUTE_BASE = "grid"
    const val GRID_ROUTE = "$GRID_ROUTE_BASE/{${SectionType.SECTION_TYPE_KEY}}"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.HOME_ROUTE
    ) {
        composable(Destinations.HOME_ROUTE) {
            HomeScreen(
                onNavigateToGrid = { sectionType ->
                    navController.navigate(
                        route = "${Destinations.GRID_ROUTE_BASE}/$sectionType"
                    )
                }
            )
        }
        composable(Destinations.GRID_ROUTE,
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