package com.vinicalgaro.cidarte.domain.repository

import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<List<Movie>>
    fun getEmCartazMovies(): Flow<List<Movie>>
    fun getTopRatedMovies(): Flow<List<Movie>>
    fun getEmBreveMovies(): Flow<List<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getWatchListMovies(): Flow<List<Movie>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetails>
    fun searchMovies(query: String): Flow<List<Movie>>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun toggleWatchlist(movie: Movie)
    fun checkMovieStatus(movieId: Int): Flow<MovieStatus>
}

data class MovieStatus(val isFavorite: Boolean, val isInWatchlist: Boolean)