package com.vinicalgaro.cidarte.presentation.screens.library

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.presentation.components.DefaultErrorComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultLoadingComponent
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.components.SectionHeader
import com.vinicalgaro.cidarte.presentation.screens.library.components.AboutSection
import com.vinicalgaro.cidarte.presentation.screens.library.components.CollectionCard
import com.vinicalgaro.cidarte.presentation.screens.library.components.UserProfileHeader
import com.vinicalgaro.cidarte.presentation.screens.library.components.cineroleta.CineRoletaBottomSheet
import com.vinicalgaro.cidarte.presentation.screens.library.components.cineroleta.CineRoletaCard
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionType
import com.vinicalgaro.cidarte.presentation.theme.CidarteRosa

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel(),
    onCollectionClick: (sectionType: String) -> Unit,
    onGoToMovieClick: (movieId: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    DefaultScaffold(hideTopBar = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            UserProfileHeader()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when {
                    uiState.isLoading -> DefaultLoadingComponent()

                    uiState.hasError -> DefaultErrorComponent(onRetry = viewModel::onRetry)

                    else -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        LibraryContent(
                            uiState = uiState,
                            viewModel = viewModel,
                            onGoToMovieClick = onGoToMovieClick,
                            onCollectionClick = onCollectionClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LibraryContent(
    uiState: LibraryUiState,
    viewModel: LibraryViewModel,
    onGoToMovieClick: (Int) -> Unit,
    onCollectionClick: (String) -> Unit,
) {
    val context = LocalContext.current

    var showCineDialog by remember { mutableStateOf(false) }
    var movieToShow by remember { mutableStateOf<Movie?>(null) }

    val hasMovies = uiState.watchListMovies.isNotEmpty() || uiState.popularMovies.isNotEmpty()
    if (hasMovies) {
        CineRoletaCard(
            onClick = {
                val sorted = viewModel.getRandomMovie()
                if (sorted != null) {
                    movieToShow = sorted
                    showCineDialog = true
                }
            }
        )
    }
    if (showCineDialog && movieToShow != null) {
        CineRoletaBottomSheet(
            onDismiss = { showCineDialog = false },
            onGoToMovie = { movieId ->
                showCineDialog = false
                onGoToMovieClick(movieId)
            },
            movie = movieToShow!!
        )
    }
    SectionHeader(stringResource(R.string.minhas_colecoes))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CollectionCard(
            title = stringResource(R.string.quero_ver),
            count = uiState.watchListMovies.size,
            icon = Lucide.Eye,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
            onClick = { onCollectionClick(SectionType.WATCHLIST) }
        )
        CollectionCard(
            title = stringResource(R.string.favoritos),
            count = uiState.favoriteMovies.size,
            icon = Lucide.Heart,
            color = CidarteRosa,
            modifier = Modifier.weight(1f),
            onClick = { onCollectionClick(SectionType.FAVORITES) }
        )
    }
    SectionHeader(stringResource(R.string.sobre))
    AboutSection(
        onLinkedinClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                context.getString(R.string.linkedi_url).toUri()
            )
            context.startActivity(intent)
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
}