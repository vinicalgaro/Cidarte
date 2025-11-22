package com.vinicalgaro.cidarte.presentation.screens.detailedmovie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.domain.usecase.GetMovieDetailsUseCase
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _movieId: Int? = savedStateHandle[AppRoutes.MOVIE_ID_KEY]
    private val _uiState = MutableStateFlow(DetailedMovieUiState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
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
}