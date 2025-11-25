package com.vinicalgaro.cidarte.data.repository

import com.vinicalgaro.cidarte.data.local.dao.MovieDao
import com.vinicalgaro.cidarte.data.local.entity.MovieEntity
import com.vinicalgaro.cidarte.data.local.entity.toDomain
import com.vinicalgaro.cidarte.data.local.entity.toEntity
import com.vinicalgaro.cidarte.data.remote.api.TmdbApi
import com.vinicalgaro.cidarte.data.remote.dto.MovieDto
import com.vinicalgaro.cidarte.data.remote.dto.TmdbListResponse
import com.vinicalgaro.cidarte.data.remote.mapper.toDomain
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.model.MovieDetails
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import com.vinicalgaro.cidarte.domain.repository.MovieStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val dao: MovieDao
) : MovieRepository {

    override fun getPopularMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getPopularMovies() })

    override fun getTopRatedMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getTopRatedMovies() })

    override fun getEmCartazMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getNowPlayingMovies() })

    override fun getEmBreveMovies(): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.getEmBreveMovies() })

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return dao.getFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWatchListMovies(): Flow<List<Movie>> {
        return dao.getWatchlist().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> = flow {
        val movieDto = api.getMovieDetails(movieId)
        val movieDetail = movieDto.toDomain()
        emit(movieDetail)
    }

    override fun searchMovies(query: String): Flow<List<Movie>> =
        getMoviesFromApi(apiCall = { api.searchMovies(query) })

    override suspend fun toggleFavorite(movie: Movie) {
        toggleMovieStatus(movie) { cached ->
            cached.copy(isFavorite = !cached.isFavorite)
        }
    }

    override suspend fun toggleWatchlist(movie: Movie) {
        toggleMovieStatus(movie) { cached ->
            cached.copy(isInWatchlist = !cached.isInWatchlist)
        }
    }

    override fun checkMovieStatus(movieId: Int): Flow<MovieStatus> {
        return dao.observeMovieById(movieId).map { entity ->
            MovieStatus(
                isFavorite = entity?.isFavorite == true,
                isInWatchlist = entity?.isInWatchlist == true
            )
        }
    }

    private suspend fun toggleMovieStatus(
        movie: Movie,
        updateAction: (MovieEntity) -> MovieEntity
    ) {
        val cached = dao.getMovieById(movie.id)
        if (cached != null) {
            val updated = updateAction(cached).copy(lastUpdated = System.currentTimeMillis())
            dao.insertOrUpdate(updated)
            if (!updated.isFavorite && !updated.isInWatchlist) {
                dao.deleteIfOrphan(movie.id)
            }
        } else {
            val newEntity = movie.toEntity(isFavorite = false, isInWatchlist = false)
            val updated = updateAction(newEntity)
            dao.insertOrUpdate(updated)
        }
    }

    private fun getMoviesFromApi(
        apiCall: suspend () -> TmdbListResponse<MovieDto>
    ): Flow<List<Movie>> = flow {
        val movies = apiCall().results.map { it.toDomain() }
        emit(movies)
    }.catch { exception ->
        emit(emptyList())
    }
}