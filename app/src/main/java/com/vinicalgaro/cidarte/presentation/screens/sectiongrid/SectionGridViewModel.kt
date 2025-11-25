package com.vinicalgaro.cidarte.presentation.screens.sectiongrid

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.R
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.usecase.GetEmBreveMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetEmCartazMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetFavoriteMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetPopularMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetTopRatedMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetWatchListMoviesUseCase
import com.vinicalgaro.cidarte.presentation.navigation.AppRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionGridViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getEmCartazMoviesUseCase: GetEmCartazMoviesUseCase,
    private val getEmBreveMoviesUseCase: GetEmBreveMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase,
    savedStateHandle: SavedStateHandle,
    private val application: Application
) : ViewModel() {
    private val _sectionType: String? =
        savedStateHandle[AppRoutes.SECTION_TYPE_KEY]
    private val _uiState = MutableStateFlow(SectionGridUiState())

    val uiState = _uiState.asStateFlow()

    init {
        observeSectionMovies()
    }

    fun observeSectionMovies() {
        val sectionType = _sectionType ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            val (flow, titleResId) = getFlowAndTitle(sectionType)

            if (flow != null && titleResId != null) {
                val titleString = application.getString(titleResId)
                _uiState.update { it.copy(sectionTitle = titleString) }
                try {
                    flow.collect { movies ->
                        _uiState.update {
                            it.copy(
                                movies = movies,
                                isLoading = false
                            )
                        }
                    }
                } catch (_: Exception) {
                    _uiState.update { it.copy(hasError = true, isLoading = false) }
                }
            } else {
                _uiState.update { it.copy(hasError = true, isLoading = false) }
            }
        }
    }

    private fun getFlowAndTitle(type: String): Pair<Flow<List<Movie>>?, Int?> {
        return try {
            when (type) {
                SectionType.POPULAR -> Pair(getPopularMoviesUseCase(), R.string.section_populares)
                SectionType.TOP_RATED -> Pair(
                    getTopRatedMoviesUseCase(),
                    R.string.section_mais_votados
                )

                SectionType.NOW_PLAYING -> Pair(
                    getEmCartazMoviesUseCase(),
                    R.string.section_em_cartaz
                )

                SectionType.UPCOMING -> Pair(getEmBreveMoviesUseCase(), R.string.section_em_breve)
                SectionType.FAVORITES -> Pair(getFavoriteMoviesUseCase(), R.string.favoritos)
                SectionType.WATCHLIST -> Pair(getWatchListMoviesUseCase(), R.string.quero_ver)
                else -> Pair(null, null)
            }
        } catch (_: Exception) {
            Pair(null, null)
        }
    }
}