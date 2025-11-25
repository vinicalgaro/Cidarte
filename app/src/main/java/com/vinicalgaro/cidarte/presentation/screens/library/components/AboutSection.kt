package com.vinicalgaro.cidarte.presentation.screens.library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowUpRight
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.Info
import com.composables.icons.lucide.Lucide
import com.vinicalgaro.cidarte.BuildConfig
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.presentation.components.ConfigItem

@Composable
fun AboutSection(onLinkedinClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
            showArrow = true,
            arrowImageVector = Lucide.ArrowUpRight
        )
    }
}