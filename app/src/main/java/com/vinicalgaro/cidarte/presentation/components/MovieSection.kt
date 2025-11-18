package com.vinicalgaro.cidarte.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val maxSizeList = 5;
    val displayMovies = movies.take(maxSizeList)
    val showSeeMore = movies.size > maxSizeList

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
            items(displayMovies) { movie ->
                MovieItem(movie = movie)
            }

            if (showSeeMore) {
                item {
                    SeeMoreItem(onClick = onSeeMoreClick)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}