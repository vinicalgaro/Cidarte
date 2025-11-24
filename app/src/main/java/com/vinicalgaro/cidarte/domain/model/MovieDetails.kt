package com.vinicalgaro.cidarte.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int,
    val duration: String?,
    val genres: List<String>,
    val countries: List<String>
)