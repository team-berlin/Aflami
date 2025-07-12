package com.berlin.aflami.viewmodel.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.MediaUiState
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

    private val _searchUIState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val searchUIState = _searchUIState.asStateFlow()

    private val _moviesUiState = MutableStateFlow(SearchMoviesUiState())
    val moviesUiState = _moviesUiState.asStateFlow()

    private val _tvShowUiState = MutableStateFlow(SearchTvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    var isSearching by mutableStateOf(false)
        private set

    fun onFocusChanged(isFocus: Boolean) {
        isSearching = isFocus
        if (isFocus) {
            _searchUIState.update {
                SearchUiState.Searching.Loading
            }
        }
    }

    var selectTabIndex by mutableIntStateOf(0)
        private set

    fun onTabChange(index: Int) {
        selectTabIndex = index
        val query = when (index) {
            0 -> tvShowUiState.value.tvShowName
            1 -> moviesUiState.value.movieName
            else -> ""
        }
        onSearchClick(query)
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onSearchClick(query: CharSequence) {
        if (query.toString().isBlank()) {
            _searchUIState.update { SearchUiState.Searching.Init }
            when (selectTabIndex) {
                0 -> _moviesUiState.update { it.copy(movieName = "") }
                1 -> _tvShowUiState.update { it.copy(tvShowName = "") }
            }
            return
        }
        when (selectTabIndex) {
            0 -> {
                _moviesUiState.update {
                    it.copy(
                        movieName = query.toString()
                    )
                }
                searchMedia(MediaType.MOVIE)
            }

            1 -> {
                _tvShowUiState.update {
                    it.copy(
                        tvShowName = query.toString()
                    )
                }
                searchMedia(MediaType.TV_SHOW)
            }
        }
    }

    override fun onMovieClick(id: Int) {
        TODO("Not yet implemented")
    }

    private fun searchMedia(mediaType: MediaType) {
        val query = when (mediaType) {
            MediaType.MOVIE -> moviesUiState.value.movieName
            MediaType.TV_SHOW -> tvShowUiState.value.tvShowName
        }
        if (query.isBlank()) return

        _searchUIState.update { SearchUiState.Searching.Loading }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = when (mediaType) {
                    MediaType.MOVIE -> {
                        searchMoviesUseCase(
                            query, languageCode
                        ).map { it.toUIState() }
                    }

                    MediaType.TV_SHOW -> {
                        searchTvShowsUseCase(
                            query, languageCode
                        ).map { it.toUiState() }
                    }
                }
                when (mediaType) {
                    MediaType.MOVIE -> onSearchMoviesSuccess(result as List<MovieUIState>)
                    MediaType.TV_SHOW -> onSearchTvShowsSuccess(result as List<TVShowUiState>)
                }

            } catch (e: Exception) {
                onSearchError(e.message ?: "Unknown error")
            }
        }
    }

    enum class MediaType {
        MOVIE, TV_SHOW
    }

    private fun onSearchMoviesSuccess(movies: List<MovieUIState>) {
        _searchUIState.update {
            SearchUiState.Searching.Success(
                movies.map {
                    MediaUiState(
                        id = it.id,
                        title = it.title,
                        rating = it.rating,
                        releaseYear = it.releaseYear,
                        genre = it.genre,
                        poster = it.poster
                    )
                }
            )
        }
    }

    private fun onSearchError(error: String) {
        _searchUIState.update { SearchUiState.Searching.Error(error) }
    }

    private fun onSearchTvShowsSuccess(tvShows: List<TVShowUiState>) {
        _searchUIState.update {
            SearchUiState.Searching.Success(
                tvShows.map {
                    MediaUiState(
                        id = it.id,
                        title = it.title,
                        rating = it.rating,
                        releaseYear = it.releaseYear,
                        genre = it.genre,
                        poster = it.poster
                    )
                }
            )
        }
    }

    fun clearSearchState() {
        _searchUIState.update { SearchUiState.Init }
    }
}