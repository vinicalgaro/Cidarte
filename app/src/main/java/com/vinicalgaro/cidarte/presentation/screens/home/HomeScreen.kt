package com.vinicalgaro.cidarte.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.vinicalgaro.cidarte.domain.model.Movie

// 1. Esta é a "tela" principal. Ela gerencia o estado.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel() // Pega o ViewModel via Hilt
) {
    // 2. Coleta o StateFlow e se redesenha automaticamente quando ele muda
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cidarte") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        // 3. Lógica para exibir o conteúdo certo (Loading, Erro ou Sucesso)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Importante aplicar o padding do Scaffold
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.error != null -> {
                    Text(
                        text = "Erro ao carregar: ${uiState.error}",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    // 4. SUCESSO: Chama o Composable que desenha o conteúdo
                    HomeContent(uiState = uiState)
                }
            }
        }
    }
}

// 5. Composable que desenha o conteúdo de sucesso (a lista de seções)
@Composable
fun HomeContent(uiState: HomeUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Seção 1: Populares
        if (uiState.popularMovies.isNotEmpty()) {
            item {
                MovieSection(
                    title = "Populares",
                    movies = uiState.popularMovies
                )
            }
        }

        // Seção 2: Em Cartaz
        if (uiState.nowPlayingMovies.isNotEmpty()) {
            item {
                MovieSection(
                    title = "Em Cartaz",
                    movies = uiState.nowPlayingMovies
                )
            }
        }

        // Seção 3: Mais Votados
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

// 6. Composable reutilizável para uma "Seção" (ex: "Populares" + lista horizontal)
@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                MovieItem(movie = movie)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// 7. Composable reutilizável para um único "Item de Filme" (o card com a imagem)
@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .width(150.dp) // Largura fixa para o pôster
    ) {
        Column {
            Image(
                // 8. COIL: Carrega a imagem da internet
                painter = rememberAsyncImagePainter(model = movie.posterUrl),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop, // Garante que a imagem preencha o espaço
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp) // Altura fixa
            )
            Text(
                text = movie.title,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp),
                maxLines = 1
            )
            Text(
                text = movie.releaseYear,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}