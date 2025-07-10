package com.berlin.aflami.viewmodel.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.TvShowUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase
) : ViewModel(), SearchInteractor {

    private val _moviesUiState = MutableStateFlow(SearchMoviesUiState())
    val movieUiState = _moviesUiState.asStateFlow()

    private val _tvShowUiState = MutableStateFlow(SearchTvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    var isSearching by mutableStateOf(false)
        private set

    fun onFocusChanged(isFocus: Boolean) {
        isSearching = isFocus
    }

    var selectTabIndex by mutableStateOf(0)
        private set

    fun onTabChange(index: Int) {
        selectTabIndex = index
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onQuerySearchChanged(query: CharSequence) {
        if (selectTabIndex == 0) {
            _moviesUiState.update { it.copy(movieName = query.toString()) }
        } else {
            _tvShowUiState.update { it.copy(tvShowName = query.toString()) }
        }
    }

    override fun onSearchClick() {
        if (selectTabIndex == 0) {
            searchMovies()
        } else {
            searchTvShows()
        }
    }

    override fun onClickMovie(id: Int) {
        TODO("Not yet implemented")
    }

    private fun searchMovies() {
        val query = movieUiState.value.movieName
        if (query.isBlank()) return

        _moviesUiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val result = searchMoviesUseCase(query).map { it.toUIState() }
                onSearchMoviesSuccess(result)
                Log.e("onSearchMoviesSuccessResult", result.toString())
            } catch (e: Exception) {
                Log.e("onSearchMoviesError", e.toString())
                onSearchMoviesError(e.message ?: "Unknown error")
            }
        }
    }

    private fun searchTvShows() {
        val query = tvShowUiState.value.tvShowName
        if (query.isBlank()) return

        _tvShowUiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val result = searchTvShowsUseCase(query).map { it.toUiState() }
                onSearchTvShowsSuccess(result)
                Log.e("onSearchTvShowsSuccessResult", result.toString())
            } catch (e: Exception) {
                Log.e("onSearchTvShowsError", e.toString())
                onSearchTvShowsError(e.message ?: "Unknown error")
            }
        }
    }

    private fun onSearchMoviesSuccess(movies: List<MovieUIState>) {
        _moviesUiState.update { it.copy(movies = movies, isLoading = false) }
    }

    private fun onSearchMoviesError(error: String) {
        _moviesUiState.update { it.copy(error = error, isLoading = false) }
    }

    private fun onSearchTvShowsSuccess(tvShows: List<TvShowUiState>) {
        _tvShowUiState.update { it.copy(tvShow = tvShows, isLoading = false) }
    }

    private fun onSearchTvShowsError(error: String) {
        _tvShowUiState.update { it.copy(error = error, isLoading = false) }
    }
}