package com.vinicalgaro.cidarte.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import java.util.Locale

@Composable
private fun BaseMovieCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val maxHeight = 225.dp
    val maxWidth = 150.dp

    Card(
        modifier = modifier
            .width(maxWidth)
            .height(maxHeight),
        onClick = onClick
    ) {
        Column(content = content)
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit = {}) {
    BaseMovieCard(onClick = onClick) {
        Image(
            painter = rememberAsyncImagePainter(model = movie.posterUrl),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = movie.title,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = getFormattedDescription(movie),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

private fun getFormattedDescription(movie: Movie): String = "${movie.releaseYear}${
    if (movie.voteAverage != null) " - ${
        String.format(
            Locale.getDefault(),
            "%.1f",
            movie.voteAverage
        )
    }" else ""
}"

@Composable
fun SeeMoreItem(onClick: () -> Unit) {
    BaseMovieCard(onClick = onClick) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = stringResource(R.string.ver_mais),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}