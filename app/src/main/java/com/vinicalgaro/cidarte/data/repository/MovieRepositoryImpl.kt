package com.vinicalgaro.cidarte.data.repository

import com.vinicalgaro.cidarte.data.remote.api.TmdbApi
import com.vinicalgaro.cidarte.data.remote.dto.MovieDto
import com.vinicalgaro.cidarte.data.remote.dto.TmdbListResponse
import com.vinicalgaro.cidarte.data.remote.mapper.toDomain
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.model.MovieDetails
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val api: TmdbApi
) : MovieRepository {

    override fun getPopularMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getPopularMovies() })

    override fun getTopRatedMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getTopRatedMovies() })

    override fun getEmCartazMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getNowPlayingMovies() })

    override fun getEmBreveMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getEmBreveMovies() })

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> = flow {
        val movieDto = api.getMovieDetails(movieId)
        val movieDetail = movieDto.toDomain()
        emit(movieDetail)
    }

    override fun searchMovies(query: String): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.searchMovies(query) })

    private fun getMoviesFromApi(
        apiCall: suspend () -> TmdbListResponse<MovieDto>
    ): Flow<List<Movie>> = flow {
        val movies = apiCall().results.map { it.toDomain() }
        emit(movies)
    }.catch { exception ->
        emit(emptyList())
    }
}