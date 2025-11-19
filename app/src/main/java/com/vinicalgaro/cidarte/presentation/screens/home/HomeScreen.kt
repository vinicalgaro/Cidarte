package com.vinicalgaro.cidarte.presentation.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.presentation.components.DefaultErrorComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultLoadingComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.components.MovieSection
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionType

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSeeMoreClick: (sectionType: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DefaultScaffold {
        when {
            uiState.isLoading -> DefaultLoadingComponent()

            uiState.hasError -> DefaultErrorComponent()

            else -> HomeContent(uiState = uiState, onSeeMoreClick)
        }
    }
}

@Composable
private fun HomeContent(uiState: HomeUiState, onSeeMoreClick: (sectionType: String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        movieListSection(
            titleResId = R.string.section_populares,
            movies = uiState.popularMovies,
            sectionType = SectionType.POPULAR,
            onSeeMoreClick = onSeeMoreClick
        )

        movieListSection(
            titleResId = R.string.section_em_cartaz,
            movies = uiState.nowPlayingMovies,
            sectionType = SectionType.NOW_PLAYING,
            onSeeMoreClick = onSeeMoreClick
        )

        movieListSection(
            titleResId = R.string.section_mais_votados,
            movies = uiState.topRatedMovies,
            sectionType = SectionType.TOP_RATED,
            onSeeMoreClick = onSeeMoreClick
        )

        movieListSection(
            titleResId = R.string.section_em_breve,
            movies = uiState.emBreveMovies,
            sectionType = SectionType.UPCOMING,
            onSeeMoreClick = onSeeMoreClick
        )
    }
}

private fun LazyListScope.movieListSection(
    @StringRes titleResId: Int,
    movies: List<Movie>,
    sectionType: String,
    onSeeMoreClick: (sectionType: String) -> Unit
) {
    if (movies.isNotEmpty()) {
        item {
            MovieSection(
                title = stringResource(id = titleResId),
                movies = movies,
                onClick = { onSeeMoreClick(sectionType) }
            )
        }
    }
}
