package com.vinicalgaro.cidarte.domain.repository

import com.vinicalgaro.cidarte.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<List<Movie>>
    fun getEmCartazMovies(): Flow<List<Movie>>
    fun getTopRatedMovies(): Flow<List<Movie>>
    fun getEmBreveMovies(): Flow<List<Movie>>
}