package com.vinicalgaro.cidarte.presentation.screens.sectiongrid

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.usecase.GetEmBreveMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetEmCartazMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetPopularMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionGridViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getEmCartazMoviesUseCase: GetEmCartazMoviesUseCase,
    private val getEmBreveMoviesUseCase: GetEmBreveMoviesUseCase,
    savedStateHandle: SavedStateHandle,
    private val application: Application
) : ViewModel() {
    private val _sectionType: String? =
        savedStateHandle[SectionType.SECTION_TYPE_KEY]
    private val _uiState = MutableStateFlow(SectionGridUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadSectionMovies(_sectionType)
    }

    private fun getMoviesBySectionType(sectionType: String?): Pair<Flow<List<Movie>>, Int>? {
        try {
            val titleResId = when (sectionType) {
                SectionType.POPULAR -> R.string.section_populares
                SectionType.TOP_RATED -> R.string.section_mais_votados
                SectionType.NOW_PLAYING -> R.string.section_em_cartaz
                SectionType.UPCOMING -> R.string.section_em_breve
                else -> throw Exception()
            }

            val useCase = when (sectionType) {
                SectionType.POPULAR -> getPopularMoviesUseCase()
                SectionType.TOP_RATED -> getTopRatedMoviesUseCase()
                SectionType.NOW_PLAYING -> getEmCartazMoviesUseCase()
                SectionType.UPCOMING -> getEmBreveMoviesUseCase()
                else -> throw Exception()
            }

            return Pair(useCase, titleResId)
        } catch (_: Exception) {
            return null
        }
    }

    private fun loadSectionMovies(sectionType: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            val pair: Pair<Flow<List<Movie>>, Int>? = getMoviesBySectionType(sectionType)

            if (pair != null) {
                val (useCase, titleResId) = pair

                val titleString = application.getString(titleResId)
                _uiState.update { it.copy(sectionTitle = titleString) }

                try {
                    val movies = useCase.first()

                    _uiState.update { it.copy(movies = movies) }
                } catch (_: Exception) {
                    _uiState.update { it.copy(hasError = true) }
                } finally {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } else {
                _uiState.update { it.copy(hasError = true, isLoading = false) }
            }
        }
    }
}