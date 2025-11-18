package com.vinicalgaro.cidarte.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vinicalgaro.cidarte.domain.model.Movie

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    onSeeMoreClick: () -> Unit = {}
) {
    val maxSizeList = 5
    val displayMovies = movies.take(maxSizeList)
    val showSeeMore = movies.size > maxSizeList
    val maxWidth = 150.dp
    val horizontalArrangement = 16.dp
    val listState = rememberLazyListState()
    val density = LocalDensity.current

    val dividerHeightFraction by remember {
        derivedStateOf {
            val itemSizePx =
                with(density) { (maxWidth + horizontalArrangement).toPx() } // Largura do Item + Espaçamento

            // Calculamos quantos pixels totais o usuário já andou
            val currentScrollPx =
                (listState.firstVisibleItemIndex * itemSizePx) + listState.firstVisibleItemScrollOffset

            // Definimos uma "meta": ao rolar o equivalente a X itens, a barra fica cheia
            val targetScrollPx = itemSizePx * 3

            // Começa em 0.5 (metade) e ganha +0.5 conforme chega na meta
            val progress = (currentScrollPx / targetScrollPx).coerceIn(0f, 1f)
            0.2f + (progress * 0.8f)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        1
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .matchParentSize(),
                contentAlignment = Alignment.TopStart
            ) {
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight(dividerHeightFraction).padding(bottom = 2.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
                LazyRow(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(horizontalArrangement)
                ) {
                    items(displayMovies) { movie ->
                        MovieItem(movie = movie, maxWidth = maxWidth)
                    }

                    if (showSeeMore) {
                        item {
                            SeeMoreItem(onClick = onSeeMoreClick, maxWidth = maxWidth)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}