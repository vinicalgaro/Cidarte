package com.vinicalgaro.cidarte.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = CidarteFundo,
    surface = CidarteSuperficie,
    primary = CidarteAcento,
    secondary = CidarteAcentoRoxo,
    surfaceVariant = CidarteDetalhe,
    onBackground = CidarteTexto,
    onSurface = CidarteTexto,
    onPrimary = CidarteTexto,
    onSecondary = CidarteTexto,
    onSurfaceVariant = CidarteTexto
)

@Composable
fun CidarteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}