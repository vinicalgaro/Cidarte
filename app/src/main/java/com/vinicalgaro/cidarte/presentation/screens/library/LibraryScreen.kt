package com.vinicalgaro.cidarte.presentation.screens.library

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.vinicalgaro.cidarte.R
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
    onCollectionClick: (sectionType: String) -> Unit,
    onGoToMovieClick: (movieId: Int) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var showCineDialog by remember { mutableStateOf(false) }

    DefaultScaffold(hideTopBar = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            UserProfileHeader()
            CineRoletaCard(
                onClick = { showCineDialog = true }
            )
            if (showCineDialog) {
                CineRoletaBottomSheet (
                    onDismiss = { showCineDialog = false },
                    onGoToMovie = { movieId -> onGoToMovieClick(movieId) }
                )
            }
            SectionHeader(stringResource(R.string.minhas_colecoes))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CollectionCard(
                    title = stringResource(R.string.quero_ver),
                    count = 0, //ToDo: Mockado
                    icon = Lucide.Eye,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    onClick = { onCollectionClick(SectionType.WATCHLIST) }
                )
                CollectionCard(
                    title = stringResource(R.string.favoritos),
                    count = 0, //ToDo: Mockado
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
    }
}