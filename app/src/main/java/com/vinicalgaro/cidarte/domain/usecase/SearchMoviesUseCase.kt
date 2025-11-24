package com.vinicalgaro.cidarte.domain.usecase

import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String): Flow<List<Movie>> =
        movieRepository.searchMovies(query)
}