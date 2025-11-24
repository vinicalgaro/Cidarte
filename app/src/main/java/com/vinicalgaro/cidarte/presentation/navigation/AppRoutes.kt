package com.vinicalgaro.cidarte.presentation.navigation

object AppRoutes {
    // --- KEYS ---
    const val SECTION_TYPE_KEY = "sectionType"
    const val MOVIE_ID_KEY = "movieId"

    // --- GRAFOS ---
    const val GRAPH_HOME = "home_graph"
    const val GRAPH_SEARCH = "search_graph"
    const val GRAPH_ABOUT = "about_graph"

    // --- TELAS DA HOME ---
    const val SCREEN_HOME = "home_screen"
    const val SCREEN_GRID_BASE = "grid_screen"
    const val SCREEN_GRID = "$SCREEN_GRID_BASE/{$SECTION_TYPE_KEY}"

    // --- TELAS DE SEARCH ---
    const val SCREEN_SEARCH = "search_screen"

    // --- TELAS DE SETTINGS ---
    const val SCREEN_ABOUT = "about_screen"

    // --- TELAS GLOBAIS ---
    const val SCREEN_MOVIE_DETAIL_BASE = "movie_detail"
    const val SCREEN_MOVIE_DETAIL = "$SCREEN_MOVIE_DETAIL_BASE/{$MOVIE_ID_KEY}"
}