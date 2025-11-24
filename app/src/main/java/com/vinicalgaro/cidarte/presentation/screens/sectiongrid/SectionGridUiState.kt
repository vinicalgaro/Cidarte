package com.vinicalgaro.cidarte.presentation.screens.sectiongrid

import com.vinicalgaro.cidarte.domain.model.Movie

data class SectionGridUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val sectionTitle: String = ""
)

object SectionType {
    const val POPULAR = "POPULAR"
    const val TOP_RATED = "TOP_RATED"
    const val NOW_PLAYING = "NOW_PLAYING"
    const val UPCOMING = "UPCOMING"
    const val FAVORITES = "FAVORITES"
    const val WATCHLIST = "WATCHLIST"
}