package com.vinicalgaro.cidarte.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("genres")
    val genres: List<GenreDto>,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)

data class ProductionCountryDto(
    @SerializedName("iso_3166_1")
    val isoCode: String,
    @SerializedName("name")
    val name: String
)