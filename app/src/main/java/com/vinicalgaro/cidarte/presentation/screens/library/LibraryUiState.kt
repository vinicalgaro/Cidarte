package com.vinicalgaro.cidarte.presentation.screens.library

import com.vinicalgaro.cidarte.domain.model.Movie

data class LibraryUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val favoriteMovies: List<Movie> = emptyList(),
    val watchListMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val memberSince: String = ""
)