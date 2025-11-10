package com.vinicalgaro.cidarte.data.remote.mapper

import com.vinicalgaro.cidarte.data.remote.dto.MovieDto
import com.vinicalgaro.cidarte.domain.model.Movie

private const val BASE_IMAGE_URL: String = "https://image.tmdb.org/t/p/"

fun MovieDto.toDomain(): Movie {
    val year: String = releaseDate?.split('-')?.firstOrNull() ?: "N/A"

    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        releaseYear = year,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        posterUrl = "${BASE_IMAGE_URL}w500${this.posterPath}",
        backdropUrl = "${BASE_IMAGE_URL}w780${this.backdropPath}"
    )
}