package com.vinicalgaro.cidarte.presentation.screens.home

import com.vinicalgaro.cidarte.domain.model.Movie

data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val emBreveMovies: List<Movie> = emptyList()
)