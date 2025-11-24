package com.vinicalgaro.cidarte.presentation.screens.detailedmovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.MovieDetails
import com.vinicalgaro.cidarte.presentation.components.BrokenImage
import com.vinicalgaro.cidarte.presentation.components.DefaultErrorComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultLoadingComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.theme.CidarteDourado
import java.util.Locale

@Composable
fun DetailedMovieScreen(
    viewModel: DetailedMovieViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val appBarTitle = uiState.movie?.title

    DefaultScaffold(
        title = appBarTitle,
        onNavigateBack = onNavigateBack
    ) {
        when {
            uiState.isLoading -> DefaultLoadingComponent()

            uiState.hasError -> DefaultErrorComponent(onRetry = viewModel::load)

            uiState.movie != null -> MovieDetailContent(movie = uiState.movie!!)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MovieDetailContent(movie: MovieDetails) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        if (movie.backdropUrl != null) MovieBanner(movie)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            MovieCabecalho(movie)
            Spacer(modifier = Modifier.height(24.dp))
            MovieGeneros(movie)
            MovieSinopse(movie)
            MovieProduzidoEm(movie)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MovieProduzidoEm(movie: MovieDetails) {
    if (movie.countries.isNotEmpty()) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(
                R.string.producao,
                movie.countries.joinToString(", ")
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun MovieSinopse(movie: MovieDetails) {
    Column {
        Text(
            text = stringResource(R.string.sinopse),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = movie.overview.ifBlank { stringResource(R.string.sinopse_nao_disponivel) },
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
            textAlign = TextAlign.Justify, // Justificado fica mais elegante em textos longos
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MovieGeneros(movie: MovieDetails) {
    if (movie.genres.isNotEmpty()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movie.genres.forEach { genre ->
                AssistChip(
                    onClick = {},
                    label = { Text(genre) }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MovieCabecalho(movie: MovieDetails) {
    @Composable
    fun MoviePoster() {
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(2f / 3f)
        ) {
            if (movie.posterUrl == null) BrokenImage() else AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun MovieInfoBadge(
        icon: ImageVector,
        text: String,
        color: Color = MaterialTheme.colorScheme.onSurface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    @Composable
    fun RowScope.MovieInfoLateral() {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val voteText = movie.voteAverage?.let {
                    String.format(Locale.US, "%.1f", it)
                } ?: "--"

                MovieInfoBadge(
                    icon = Icons.Default.Star,
                    text = voteText,
                    color = CidarteDourado
                )

                MovieInfoBadge(
                    icon = Icons.Default.Schedule,
                    text = movie.duration ?: "-- min"
                )
            }

            // Data de Lançamento - Tratamento de Null
            MovieInfoBadge(
                icon = Icons.Default.CalendarToday,
                text = movie.releaseDate ?: "--"
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MoviePoster()
        MovieInfoLateral()
    }
}

@Composable
private fun MovieBanner(movie: MovieDetails) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = movie.backdropUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        ),
                        startY = 100f
                    )
                )
        )
    }
}