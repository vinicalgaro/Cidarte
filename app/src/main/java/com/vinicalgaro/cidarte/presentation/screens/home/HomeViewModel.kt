package com.vinicalgaro.cidarte.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.domain.usecase.GetEmBreveMoviesUseCase
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
    private val getEmCartazMoviesUseCase: GetEmCartazMoviesUseCase,
    private val getEmBreveMoviesUseCase: GetEmBreveMoviesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadAllMovies()
    }

    fun loadAllMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            try {
                val popularDeferred = async { getPopularMoviesUseCase().first() }
                val topRatedDeferred = async { getTopRatedMoviesUseCase().first() }
                val nowPlayingDeferred = async { getEmCartazMoviesUseCase().first() }
                val emBreveDeferred = async { getEmBreveMoviesUseCase().first() }

                val popularMovies = popularDeferred.await()
                val topRatedMovies = topRatedDeferred.await()
                val nowPlayingMovies = nowPlayingDeferred.await()
                val emBreveMovies = emBreveDeferred.await()

                _uiState.update {
                    it.copy(
                        popularMovies = popularMovies,
                        topRatedMovies = topRatedMovies,
                        nowPlayingMovies = nowPlayingMovies,
                        emBreveMovies = emBreveMovies
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(hasError = true)
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}