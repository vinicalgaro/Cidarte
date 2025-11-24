package com.vinicalgaro.cidarte.presentation.screens.search

import com.vinicalgaro.cidarte.domain.model.Movie

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val suggestedMovies: List<Movie> = emptyList(),
    val isInitialState: Boolean = true
)