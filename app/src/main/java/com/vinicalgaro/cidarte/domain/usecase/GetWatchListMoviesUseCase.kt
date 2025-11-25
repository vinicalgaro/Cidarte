package com.vinicalgaro.cidarte.domain.usecase

import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchListMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = movieRepository.getWatchListMovies()
}