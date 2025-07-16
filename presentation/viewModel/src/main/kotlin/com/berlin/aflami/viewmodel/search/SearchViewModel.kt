package com.berlin.aflami.viewmodel.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.search_actor.FilterUiState
import com.berlin.aflami.viewmodel.search_actor.GenreType
import com.berlin.aflami.viewmodel.search_actor.genreToId
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.ClearSearchHistoryUseCase
import usecase.DeleteQueryFromHistoryUseCase
import usecase.GetRecentHistoryUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.SaveRecentHistoryUseCase
import java.util.Locale

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase,
    private val getRecentHistoryUseCase: GetRecentHistoryUseCase,
    private val saveRecentHistoryUseCase: SaveRecentHistoryUseCase,
    private val deleteQueryFromHistoryUseCase: DeleteQueryFromHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,

    ) : ViewModel(), SearchInteractionListener {

    private val _searchUIState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val searchUIState = _searchUIState.asStateFlow()

    private val _filterUiState = MutableStateFlow(FilterUiState())
    val filterUiState = _filterUiState.asStateFlow()

    private val _filterDialogState = MutableStateFlow(false)
    val filterDialogState = _filterDialogState.asStateFlow()

    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow

    private val _recentSearchState = MutableStateFlow<List<String>>(emptyList())
    val recentSearchState = _recentSearchState.asStateFlow()


    init {
        viewModelScope.launch {
            _queryFlow
                .debounce(600)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect { query ->
                    onSearchClick(query)
                }
        }

        loadRecentSearches()
    }

    fun updateRating(rating: Float) {
        viewModelScope.launch {
            _filterUiState.update { it.copy(selectedRating = rating) }
        }
    }

    fun toggleGenre(genre: GenreType) {
        viewModelScope.launch {
            _filterUiState.update { current ->
                val currentGenres = current.selectedGenre.type
                val updatedGenres = if (genre == GenreType.ALL) {
                    GenreType.ALL
                } else if (currentGenres == GenreType.ALL) {
                    genre
                } else {
                    if (currentGenres == genre) GenreType.ALL else genre
                }
                current.copy(selectedGenre = current.selectedGenre.copy(type = updatedGenres))
            }
        }
    }

    fun onFocusChanged(isFocus: Boolean) {
        if (isFocus) {
            _searchUIState.update {
                SearchUiState.Searching.Init
            }
        }
    }

    var selectTabIndex by mutableIntStateOf(0)
        private set

    fun onTabChange(index: Int) {
        selectTabIndex = index
        updateSearchQuery(_queryFlow.value)
    }

    override fun onBackClick() {
        clearSearchState()
    }

    fun updateSearchQuery(query: String) {
        _queryFlow.update { query }
    }

    override fun onSearchClick(query: CharSequence) {
        val queryData = query.toString().trim()
        if (query.toString().isBlank()) {
            _searchUIState.update { SearchUiState.Searching.Init }
            return
        }
        _queryFlow.update { queryData }
        when (selectTabIndex) {
            0 -> searchMedia(MediaType.MOVIE)
            1 -> searchMedia(MediaType.TV_SHOW)
        }
    }


    override fun onFilterIconClicked() {
        _filterDialogState.update { true }
    }

    fun onItemClicked(query: String) {
        updateSearchQuery(query)
        onSearchClick(query)
    }

    private fun searchMedia(mediaType: MediaType) {

        if (queryFlow.value.isBlank()) return
        _searchUIState.update { SearchUiState.Searching.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locale = Locale.getDefault()
                val languageCode = "${locale.language}-${locale.country}"
                val result = when (mediaType) {
                    MediaType.MOVIE -> {
                        searchMoviesUseCase(
                            queryFlow.value, languageCode
                        ).map {
                            it.toUIState()
                        }.filter {
                            Log.d("Search", it.rating)
                            (it.rating.toDouble() >= _filterUiState.value.selectedRating.toDouble())
                                    &&
                                    (if (genreToId(_filterUiState.value.selectedGenre.type) > 0) {
                                        it.genre.contains(
                                            genreToId(_filterUiState.value.selectedGenre.type)
                                        )
                                    } else {
                                        true
                                    }
                                            )
                        }
                    }

                    MediaType.TV_SHOW -> {
                        searchTvShowsUseCase(queryFlow.value, languageCode)
                            .map { it.toUiState() }
                            .filter {
                                (it.rating.toDouble() >= _filterUiState.value.selectedRating.toDouble())
                                        &&
                                        if (genreToId(_filterUiState.value.selectedGenre.type) > 0) {
                                            it.genre.contains(
                                                genreToId(_filterUiState.value.selectedGenre.type)
                                            )
                                        } else {
                                            true
                                        }
                            }
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
        viewModelScope.launch {
            saveRecentHistoryUseCase(queryFlow.value)
            loadRecentSearches()
        }

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
        viewModelScope.launch {
            saveRecentHistoryUseCase(queryFlow.value)
            loadRecentSearches()
        }
    }


    private fun onSearchError(error: String) {
        _searchUIState.update { SearchUiState.Searching.Error(error) }
    }

    fun onDismiss() {
        _filterDialogState.update { false }
    }

    fun clearFilters() {
        viewModelScope.launch {
            _filterUiState.update {
                FilterUiState()
            }
        }
    }

    fun clearSearchState() {
        _searchUIState.update { SearchUiState.Init }
        _queryFlow.value = ""
    }

    fun loadRecentSearches() {
        viewModelScope.launch {
            val recentHistoryQueries = getRecentHistoryUseCase()
            _recentSearchState.value = recentHistoryQueries
        }
    }
    fun deleteQueryFromHistory(query: String) {
        viewModelScope.launch {
            deleteQueryFromHistoryUseCase(query)
            loadRecentSearches()
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            clearSearchHistoryUseCase()
            loadRecentSearches()
        }
    }

}

