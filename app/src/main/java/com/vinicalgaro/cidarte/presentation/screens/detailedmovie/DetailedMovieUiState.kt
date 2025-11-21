package com.vinicalgaro.cidarte.presentation.screens.detailedmovie

import com.vinicalgaro.cidarte.domain.model.Movie

data class DetailedMovieUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val movie: Movie
)