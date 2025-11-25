package com.vinicalgaro.cidarte.presentation.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicalgaro.cidarte.data.local.UserPreferences
import com.vinicalgaro.cidarte.domain.model.Movie
import com.vinicalgaro.cidarte.domain.usecase.GetFavoriteMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetPopularMoviesUseCase
import com.vinicalgaro.cidarte.domain.usecase.GetWatchListMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    getWatchListMoviesUseCase: GetWatchListMoviesUseCase,
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    userPreferences: UserPreferences
) : ViewModel() {
    private val memberSinceYear = userPreferences.getMemberSince()
    private val retryTrigger = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<LibraryUiState> = retryTrigger.flatMapLatest {
        combine(
            getFavoriteMoviesUseCase(), getWatchListMoviesUseCase(), getPopularMoviesUseCase()
        ) { favorites, watchlist, popular ->
            LibraryUiState(
                isLoading = false,
                favoriteMovies = favorites,
                watchListMovies = watchlist,
                popularMovies = popular,
                memberSince = memberSinceYear
            )
        }.catch {
            emit(LibraryUiState(hasError = true))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LibraryUiState(isLoading = true)
    )
    private var lastPickedMovieId: Int? = null

    fun getRandomMovie(): Movie? {
        val currentState = uiState.value

        val sourceList = if (currentState.watchListMovies.isNotEmpty()) {
            currentState.watchListMovies
        } else {
            currentState.popularMovies
        }

        if (sourceList.isEmpty()) return null

        val candidates = if (sourceList.size > 1 && lastPickedMovieId != null) {
            sourceList.filter { it.id != lastPickedMovieId }
        } else {
            sourceList
        }

        val selectedMovie = candidates.random()
        lastPickedMovieId = selectedMovie.id

        return selectedMovie
    }

    fun onRetry() {
        retryTrigger.value += 1
    }
}