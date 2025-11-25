package com.vinicalgaro.cidarte.presentation.screens.library.components.cineroleta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Shuffle
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.presentation.components.DefaultCard
import com.vinicalgaro.cidarte.presentation.theme.CidarteDourado

@Composable
fun CineRoletaCard(onClick: () -> Unit) {
    DefaultCard(
        modifier = Modifier.fillMaxWidth(),
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
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
                Text(
                    text = stringResource(R.string.sortear_filme),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            IconButton(
                onClick = onClick,
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