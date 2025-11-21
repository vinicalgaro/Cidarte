package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinicalgaro.cidarte.presentation.components.CidarteBottomNavigationBar
import com.vinicalgaro.cidarte.presentation.screens.home.HomeScreen
import com.vinicalgaro.cidarte.presentation.screens.search.SearchScreen
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionGridScreen
import com.vinicalgaro.cidarte.presentation.screens.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            CidarteBottomNavigationBar(navController = navController)
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.GRAPH_HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            navigation(
                route = AppRoutes.GRAPH_HOME,
                startDestination = AppRoutes.SCREEN_HOME
            ) {
                composable(AppRoutes.SCREEN_HOME) {
                    HomeScreen(
                        onSeeMoreClick = { sectionType ->
                            navController.navigate("${AppRoutes.SCREEN_GRID_BASE}/$sectionType")
                        }
                    )
                }

                composable(
                    route = AppRoutes.SCREEN_GRID,
                    arguments = listOf(navArgument(AppRoutes.SECTION_TYPE_KEY) {
                        type = NavType.StringType
                    })
                ) {
                    SectionGridScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
            navigation(
                route = AppRoutes.GRAPH_SEARCH,
                startDestination = AppRoutes.SCREEN_SEARCH
            ) {
                composable(AppRoutes.SCREEN_SEARCH) {
                    SearchScreen()
                }
            }
            navigation(
                route = AppRoutes.GRAPH_SETTINGS,
                startDestination = AppRoutes.SCREEN_SETTINGS
            ) {
                composable(AppRoutes.SCREEN_SETTINGS) {
                    SettingsScreen()
                }
            }
            composable(
                route = AppRoutes.SCREEN_MOVIE_DETAIL,
                arguments = listOf(navArgument(AppRoutes.MOVIE_ID_KEY) { type = NavType.IntType })
            ) {}
        }
    }
}