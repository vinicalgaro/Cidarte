package com.vinicalgaro.cidarte.presentation.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.domain.usecase.GetFavoriteMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetPopularMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetWatchListMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            try {
                val favoriteMoviesDeferred = async { getFavoriteMoviesUseCase() }
                val watchListMoviesDeferred = async { getWatchListMoviesUseCase() }
                val popularMoviesDeferred = async { getPopularMoviesUseCase() }

                val favoriteMovies = favoriteMoviesDeferred.await()
                val watchListMovies = watchListMoviesDeferred.await()
                val popularMovies = popularMoviesDeferred.await()

                _uiState.update {
                    it.copy(
                        favoriteMovies = favoriteMovies.first(),
                        watchListMovies = watchListMovies.first(),
                        popularMovies = popularMovies.first()
                    )
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(hasError = true) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}