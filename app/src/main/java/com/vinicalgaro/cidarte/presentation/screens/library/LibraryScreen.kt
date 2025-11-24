package com.vinicalgaro.cidarte.presentation.screens.library

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.CircleUserRound
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Info
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Shuffle
import com.vinicalgaro.cidarte.BuildConfig
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.presentation.components.CollectionCard
import com.vinicalgaro.cidarte.presentation.components.ConfigItem
import com.vinicalgaro.cidarte.presentation.components.DefaultScaffold
import com.vinicalgaro.cidarte.presentation.components.SectionHeader
import com.vinicalgaro.cidarte.presentation.screens.sectiongrid.SectionType
import com.vinicalgaro.cidarte.presentation.theme.CidarteDourado
import com.vinicalgaro.cidarte.presentation.theme.CidarteRosa

@Composable
fun LibraryScreen(
    onCollectionClick: (collectionId: String) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    DefaultScaffold(hideTopBar = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            UserProfileHeader()
            CineRoletaCard()
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

@Composable
fun UserProfileHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Lucide.CircleUserRound,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = stringResource(R.string.cinefilo), //ToDo: Mockado - possibilitar edição
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.membro_desde, 2025), //ToDo: Mockado
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun CineRoletaCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.nao_sabe_o_que_ver),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    text = stringResource(R.string.girar_a_roleta),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            IconButton(
                onClick = { /* ToDo: Implementar lógica de random */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(CidarteDourado, CircleShape)
            ) {
                Icon(
                    imageVector = Lucide.Shuffle,
                    contentDescription = stringResource(R.string.sortear_filme),
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun AboutSection(onLinkedinClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        ConfigItem(
            label = stringResource(R.string.versao_do_app),
            value = BuildConfig.VERSION_NAME,
            icon = Lucide.Info,
            onClick = {}
        )
        ConfigItem(
            label = stringResource(R.string.desenvolvido_por),
            value = stringResource(R.string.vinicius_calgaro_linkedin),
            icon = Lucide.CircleUser,
            onClick = onLinkedinClick,
            showArrow = true
        )
    }
}