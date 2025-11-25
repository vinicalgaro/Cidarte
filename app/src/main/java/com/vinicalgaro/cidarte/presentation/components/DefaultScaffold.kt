package com.vinicalgaro.cidarte.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.presentation.theme.BebasNeue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    title: String? = null,
    onNavigateBack: (() -> Unit)? = null,
    hideTopBar: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    val isHome = title == null

    return Scaffold(
        modifier = modifier,
        topBar = {
            if (!hideTopBar)
                CenterAlignedTopAppBar(
                    actions = actions,
                    title = {
                        val defaultStyle = MaterialTheme.typography.titleLarge

                        Text(
                            text = if (isHome) stringResource(id = R.string.app_name) else title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = if (isHome) {
                                defaultStyle.copy(
                                    fontFamily = BebasNeue,
                                    fontSize = 32.sp
                                )
                            } else {
                                defaultStyle.copy(
                                    fontSize = 24.sp
                                )
                            }
                        )
                    },
                    navigationIcon = {
                        onNavigateBack?.let { callback ->
                            IconButton(onClick = callback) {
                                Icon(
                                    imageVector = Lucide.ChevronLeft,
                                    contentDescription = stringResource(R.string.voltar)
                                )
                            }
                        }
                    }
                )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}