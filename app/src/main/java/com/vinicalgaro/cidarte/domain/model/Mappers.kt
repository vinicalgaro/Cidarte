package com.vinicalgaro.cidarte.domain.model

fun MovieDetails.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        releaseYear = this.releaseDate?.split("-")?.firstOrNull() ?: "",
        voteAverage = this.voteAverage,
        posterUrl = this.posterUrl
    )
}