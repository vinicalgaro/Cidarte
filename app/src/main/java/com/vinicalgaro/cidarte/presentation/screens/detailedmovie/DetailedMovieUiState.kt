package com.vinicalgaro.cidarte.presentation.screens.detailedmovie

import com.vinicalgaro.cidarte.domain.model.MovieDetails

data class DetailedMovieUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val movie: MovieDetails? = null,
    val isFavorite: Boolean = false,
    val isInWatchlist: Boolean = false
)