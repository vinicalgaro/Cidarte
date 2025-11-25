package com.vinicalgaro.cidarte.domain.usecase

import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        movieRepository.toggleFavorite(movie)
    }
}