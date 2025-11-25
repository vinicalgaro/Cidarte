package com.vinicalgaro.cidarte.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vinicalgaro.cidarte.domain.model.Movie

@Entity(tableName = "movies_cache")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String?,
    val releaseYear: String,
    val voteAverage: Double?,
    val isFavorite: Boolean = false,
    val isInWatchlist: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        releaseYear = releaseYear,
        voteAverage = voteAverage,
        posterUrl = posterUrl
    )
}

fun Movie.toEntity(isFavorite: Boolean, isInWatchlist: Boolean): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterUrl = posterUrl,
        releaseYear = releaseYear,
        voteAverage = voteAverage,
        isFavorite = isFavorite,
        isInWatchlist = isInWatchlist,
        lastUpdated = System.currentTimeMillis()
    )
}