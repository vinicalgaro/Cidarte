package com.vinicalgaro.cidarte.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinicalgaro.cidarte.presentation.components.CidarteBottomNavigationBar
import com.vinicalgaro.cidarte.presentation.screens.detailedmovie.DetailedMovieScreen
import com.vinicalgaro.cidarte.presentation.screens.home.HomeScreen
import com.vinicalgaro.cidarte.presentation.screens.search.SearchScreen
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionGridScreen
import com.vinicalgaro.cidarte.presentation.screens.library.LibraryScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val topLevelDestinations = listOf(
        AppRoutes.GRAPH_HOME,
        AppRoutes.GRAPH_SEARCH,
        AppRoutes.GRAPH_LIBRARY
    )

    val showBottomBar = currentDestination?.hierarchy?.any { destination ->
        destination.route in topLevelDestinations
    } == true

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
                            navigateToSectionGrid(navController, sectionType)
                        },
                        onMovieClick = { movieId ->
                            navigateToMovieDetails(navController, movieId)
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
                        onNavigateBack = { navController.popBackStack() },
                        onMovieClick = { movieId ->
                            navigateToMovieDetails(navController, movieId)
                        }
                    )
                }
            }
            navigation(
                route = AppRoutes.GRAPH_SEARCH,
                startDestination = AppRoutes.SCREEN_SEARCH
            ) {
                composable(AppRoutes.SCREEN_SEARCH) {
                    SearchScreen(onMovieClick = { movieId ->
                        navigateToMovieDetails(navController, movieId)
                    })
                }
            }
            navigation(
                route = AppRoutes.GRAPH_LIBRARY,
                startDestination = AppRoutes.SCREEN_LIBRARY
            ) {
                composable(AppRoutes.SCREEN_LIBRARY) {
                    LibraryScreen()
                }
            }
            composable(
                route = AppRoutes.SCREEN_MOVIE_DETAIL,
                arguments = listOf(navArgument(AppRoutes.MOVIE_ID_KEY) {
                    type = NavType.IntType
                })
            ) {
                DetailedMovieScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

private fun navigateToMovieDetails(navController: NavHostController, movieId: Int) =
    navController.navigate("${AppRoutes.SCREEN_MOVIE_DETAIL_BASE}/$movieId")

private fun navigateToSectionGrid(navController: NavHostController, sectionType: String) =
    navController.navigate("${AppRoutes.SCREEN_GRID_BASE}/$sectionType")