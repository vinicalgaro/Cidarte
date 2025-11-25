package com.vinicalgaro.cidarte.domain.usecase

import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleWatchListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        movieRepository.toggleWatchlist(movie)
    }
}