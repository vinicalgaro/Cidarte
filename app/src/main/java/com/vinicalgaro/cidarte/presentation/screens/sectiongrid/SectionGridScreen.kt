package com.vinicalgaro.cidarte.presentation.screens.sectiongrid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.presentation.components.DefaultErrorComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultLoadingComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.components.MovieItem

@Composable
fun SectionGridScreen(
    viewModel: SectionGridViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick: (movieId: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DefaultScaffold(
        title = uiState.sectionTitle,
        onNavigateBack = onNavigateBack
    ) {
        when {
            uiState.isLoading -> DefaultLoadingComponent()

            uiState.hasError -> DefaultErrorComponent(onRetry = viewModel::loadSectionMovies)

            else -> SectionGridContent(uiState.movies, onMovieClick)
        }
    }
}

@Composable
private fun SectionGridContent(movies: List<Movie>, onMovieClick: (movieId: Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies) { m ->
            MovieItem(movie = m, 120.dp, onlyImage = true, onClick = { onMovieClick(m.id) })
        }
    }
}