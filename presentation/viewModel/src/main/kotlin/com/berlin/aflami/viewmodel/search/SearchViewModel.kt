package com.berlin.aflami.viewmodel.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import java.util.Locale

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase
) : ViewModel(), SearchInteractionListener {

    private val _moviesUiState = MutableStateFlow(SearchMoviesUiState())
    val moviesUiState = _moviesUiState.asStateFlow()

    private val _tvShowUiState = MutableStateFlow(SearchTvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    var isSearching by mutableStateOf(false)
        private set

    fun onFocusChanged(isFocus: Boolean) {
        isSearching = isFocus
    }

    var selectTabIndex by mutableIntStateOf(0)
        private set

    fun onTabChange(index: Int) {
        selectTabIndex = index
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onQuerySearchChange(query: CharSequence) {
        when (selectTabIndex) {
            0 -> _moviesUiState.update {
                it.copy(
                    movieName = query.toString(), searchCompleted = false
                )
            }

            1 -> _tvShowUiState.update {
                it.copy(
                    tvShowName = query.toString(), searchCompleted = false
                )
            }
        }
    }

    override fun onSearchClick() {
        when (selectTabIndex) {
            0 -> searchMovies()
            1 -> searchTvShows()
        }
    }

    override fun onMovieClick(id: Int) {
        TODO("Not yet implemented")
    }

    private fun searchMovies() {
        val query = moviesUiState.value.movieName
        if (query.isBlank()) return

        _moviesUiState.update { it.copy(isLoading = true, error = null, searchCompleted = false) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = searchMoviesUseCase(
                    query, languageCode
                ).map { it.toUIState() }
                onSearchMoviesSuccess(result)
            } catch (e: Exception) {
                onSearchMoviesError(e.message ?: "Unknown error")
            }
        }
    }

    private fun searchTvShows() {
        val query = tvShowUiState.value.tvShowName
        if (query.isBlank()) return

        _tvShowUiState.update { it.copy(isLoading = true, error = null, searchCompleted = false) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = searchTvShowsUseCase(
                    query, languageCode
                ).map { it.toUiState() }
                onSearchTvShowsSuccess(result)
            } catch (e: Exception) {
                onSearchTvShowsError(e.message ?: "Unknown error")
            }
        }
    }

    private fun onSearchMoviesSuccess(movies: List<MovieUIState>) {
        _moviesUiState.update {
            it.copy(
                movies = movies,
                isLoading = false,
            )
        }
    }

    private fun onSearchMoviesError(error: String) {
        _moviesUiState.update { it.copy(error = error, isLoading = false) }
    }

    private fun onSearchTvShowsSuccess(tvShows: List<TVShowUiState>) {
        _tvShowUiState.update {
            it.copy(
                tvShow = tvShows,
                isLoading = false,
            )
        }
    }

    private fun onSearchTvShowsError(error: String) {
        _tvShowUiState.update { it.copy(error = error, isLoading = false) }
    }

    fun clearSearchState() {
        when (selectTabIndex) {
            0 -> _moviesUiState.update {
                it.copy(
                    movieName = "",
                    movies = emptyList(),
                    isLoading = false,
                    error = null,
                    searchCompleted = false
                )
            }

            1 -> _tvShowUiState.update {
                it.copy(
                    tvShowName = "",
                    tvShow = emptyList(),
                    isLoading = false,
                    error = null,
                    searchCompleted = false
                )
            }
        }
        isSearching = false
    }
}