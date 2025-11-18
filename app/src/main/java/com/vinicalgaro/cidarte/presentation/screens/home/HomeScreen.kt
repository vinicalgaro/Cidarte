package com.vinicalgaro.cidarte.presentation.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.components.MovieSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DefaultScaffold {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Text(
                    text = stringResource(R.string.error_ao_carregar_conteudo),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            else -> {
                HomeContent(uiState = uiState)
            }
        }
    }
}

@Composable
fun HomeContent(uiState: HomeUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        movieListSection(
            titleResId = R.string.section_populares,
            movies = uiState.popularMovies
        )

        movieListSection(
            titleResId = R.string.section_em_cartaz,
            movies = uiState.nowPlayingMovies
        )

        movieListSection(
            titleResId = R.string.section_mais_votados,
            movies = uiState.topRatedMovies
        )

        movieListSection(
            titleResId = R.string.section_em_breve,
            movies = uiState.emBreveMovies
        )
    }
}

private fun LazyListScope.movieListSection(
    @StringRes titleResId: Int,
    movies: List<Movie>
) {
    if (movies.isNotEmpty()) {
        item {
            MovieSection(
                title = stringResource(id = titleResId),
                movies = movies
            )
        }
    }
}
