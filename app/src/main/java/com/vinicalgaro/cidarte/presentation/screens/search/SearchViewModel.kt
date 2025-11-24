package com.vinicalgaro.cidarte.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.domain.usecase.GetPopularMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        loadSuggestions()
        observeSearchQuery()
    }

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        _uiState.update { it.copy(query = newQuery) }
    }

    private fun loadSuggestions() {
        viewModelScope.launch {
            try {
                val popularMovies = getPopularMoviesUseCase().first()
                val randomSuggestions = popularMovies.shuffled().take(4)

                _uiState.update {
                    it.copy(suggestedMovies = randomSuggestions)
                }
            } catch (_: Exception) {
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) {
                        _uiState.update {
                            it.copy(movies = emptyList(), isInitialState = true, isLoading = false)
                        }
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false, isInitialState = false) }

            try {
                val movies = searchMoviesUseCase(query).first()
                _uiState.update {
                    it.copy(movies = movies, isLoading = false)
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, hasError = true)
                }
            }
        }
    }
}