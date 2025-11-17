package com.vinicalgaro.cidarte.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
                    text = "Houve um erro ao carregar o conteúdo...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
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
        modifier = Modifier.fillMaxSize().padding(top = 16.dp),
    ) {
        if (uiState.popularMovies.isNotEmpty()) {
            item {
                MovieSection(
                    title = "Populares",
                    movies = uiState.popularMovies
                )
            }
        }

        if (uiState.nowPlayingMovies.isNotEmpty()) {
            item {
                MovieSection(
                    title = "Em Cartaz",
                    movies = uiState.nowPlayingMovies
                )
            }
        }

        if (uiState.topRatedMovies.isNotEmpty()) {
            item {
                MovieSection(
                    title = "Mais Votados",
                    movies = uiState.topRatedMovies
                )
            }
        }
    }
}
