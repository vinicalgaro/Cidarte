package com.vinicalgaro.cidarte.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.X
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.presentation.components.DefaultErrorComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultLoadingComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.components.MovieItem

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(), onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DefaultScaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                query = uiState.query, onQueryChange = viewModel::onQueryChange
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> DefaultLoadingComponent()

                    uiState.hasError -> DefaultErrorComponent(
                        onRetry = { viewModel.onQueryChange(uiState.query) })

                    uiState.isInitialState -> SuggestedMoviesState(
                        movies = uiState.suggestedMovies, onMovieClick = onMovieClick
                    )

                    uiState.movies.isEmpty() ->
                        DefaultErrorComponent(text = stringResource(R.string.ops_nenhum_filme_encontrado))

                    else -> SearchResultsGrid(
                        movies = uiState.movies, onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String, onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(
                stringResource(R.string.qual_filme_voce_quer_pesquisar),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = { Icon(Lucide.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        Lucide.X,
                        contentDescription = stringResource(R.string.limpar_busca)
                    )
                }
            }
        },
        shape = MaterialTheme.shapes.medium,
        singleLine = true
    )
}

@Composable
fun SearchResultsGrid(
    movies: List<Movie>, onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieItem(
                movie = movie,
                maxWidth = 120.dp,
                onClick = { onMovieClick(movie.id) },
                onlyImage = true
            )
        }
    }
}

@Composable
fun SuggestedMoviesState(
    movies: List<Movie>, onMovieClick: (Int) -> Unit
) {
    if (movies.isEmpty()) return

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = stringResource(R.string.descubra_algo_novo),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(movies, key = { it.id }) { movie ->
            MovieItem(
                movie = movie,
                maxWidth = 200.dp,
                onClick = { onMovieClick(movie.id) },
                onlyImage = true
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}