package com.vinicalgaro.cidarte.domain.usecase

import com.vinicalgaro.cidarte.domain.model.MovieDetails
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<MovieDetails> =
        movieRepository.getMovieDetails(movieId)
}