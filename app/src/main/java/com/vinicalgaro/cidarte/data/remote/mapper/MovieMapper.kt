package com.vinicalgaro.cidarte.data.remote.mapper

import com.vinicalgaro.cidarte.data.remote.dto.MovieDto
import com.vinicalgaro.cidarte.domain.model.Movie

const val BASE_IMAGE_URL: String = "https://image.tmdb.org/t/p/"

fun MovieDto.toDomain(): Movie {
    val year: String = releaseDate.split('-').firstOrNull() ?: ""

    return Movie(
        id = this.id,
        title = this.title,
        releaseYear = year,
        voteAverage = if (this.voteAverage == 0.0) null else this.voteAverage,
        posterUrl = "${BASE_IMAGE_URL}w500${this.posterPath}",
    )
}