package com.vinicalgaro.cidarte.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.domain.usecase.GetEmCartazMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetPopularMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getEmCartazMoviesUseCase: GetEmCartazMoviesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadAllMovies()
    }

    private fun loadAllMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val popularDeferred = async { getPopularMoviesUseCase().first() }
                val topRatedDeferred = async { getTopRatedMoviesUseCase().first() }
                val nowPlayingDeferred = async { getEmCartazMoviesUseCase().first() }

                val popularMovies = popularDeferred.await()
                val topRatedMovies = topRatedDeferred.await()
                val nowPlayingMovies = nowPlayingDeferred.await()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        popularMovies = popularMovies,
                        topRatedMovies = topRatedMovies,
                        nowPlayingMovies = nowPlayingMovies
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ocorreu um erro desconhecido"
                    )
                }
            }
        }
    }
}