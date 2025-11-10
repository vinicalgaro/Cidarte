package com.vinicalgaro.cidarte.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseYear: String,
    val voteAverage: Double,
    val voteCount: Int,
    val posterUrl: String,
    val backdropUrl: String
)