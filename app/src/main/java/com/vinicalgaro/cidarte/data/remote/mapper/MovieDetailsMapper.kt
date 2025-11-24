package com.vinicalgaro.cidarte.data.remote.mapper

import com.vinicalgaro.cidarte.data.remote.dto.MovieDetailsDto
import com.vinicalgaro.cidarte.domain.model.MovieDetails

fun MovieDetailsDto.toDomain(): MovieDetails {
    val formattedDate = try {
        val parts = releaseDate?.split('-')
        if (parts?.size == 3) {
            "${parts[2]}/${parts[1]}/${parts[0]}"
        } else {
            releaseDate
        }
    } catch (_: Exception) {
        null
    }

    val durationFormatted = if (runtime != null && runtime > 0) {
        val hours = runtime / 60
        val minutes = runtime % 60
        "${hours}h ${minutes}m"
    } else {
        null
    }

    return MovieDetails(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = if (this.posterPath == null)
            null else "${BASE_IMAGE_URL}w500${this.posterPath}",
        backdropUrl = if (this.backdropPath == null)
            null else "${BASE_IMAGE_URL}w1280${this.backdropPath}",
        releaseDate = formattedDate,
        voteAverage = if (this.voteAverage == 0.0) null else this.voteAverage,
        voteCount = this.voteCount,
        duration = durationFormatted,
        genres = this.genres.map { it.name },
        countries = this.productionCountries.map { it.name }
    )
}