package com.vinicalgaro.cidarte.presentation.screens.detailedmovie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.model.toMovie
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import com.vinicalgaro.cidarte.domain.usecase.GetMovieDetailsUseCase
import com.vinicalgaro.cidarte.domain.usecase.ToggleFavoriteUseCase
import com.vinicalgaro.cidarte.domain.usecase.ToggleWatchListUseCase
import com.vinicalgaro.cidarte.presentation.navigation.AppRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleWatchListUseCase: ToggleWatchListUseCase,
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _movieId: Int? = savedStateHandle[AppRoutes.MOVIE_ID_KEY]
    private val _uiState = MutableStateFlow(DetailedMovieUiState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
        observeMovieStatus()
    }

    private fun observeMovieStatus() {
        if (_movieId == null) return

        viewModelScope.launch {
            movieRepository.checkMovieStatus(_movieId).collect { status ->
                _uiState.update {
                    it.copy(
                        isFavorite = status.isFavorite,
                        isInWatchlist = status.isInWatchlist
                    )
                }
            }
        }
    }

    fun load() {
        if (_movieId == null) {
            _uiState.update { it.copy(isLoading = false, hasError = true) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            try {
                val movie = getMovieDetailsUseCase(_movieId).first()
                _uiState.update { it.copy(movie = movie) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(hasError = true) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onToggleFavorite() {
        toggleMovieState { toggleFavoriteUseCase(it) }
    }

    fun onToggleWatchlist() {
        toggleMovieState { toggleWatchListUseCase(it) }
    }

    private fun toggleMovieState(useCase: suspend (Movie) -> Unit) {
        val movieDetails = _uiState.value.movie ?: return
        val movieDomain = movieDetails.toMovie()

        viewModelScope.launch {
            useCase(movieDomain)
        }
    }
}