package com.berlin.aflami.viewmodel.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.uistate.FilterUiState
import com.berlin.aflami.viewmodel.uistate.GenreType
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
) : ViewModel(), SearchInteractionListener {

    private val _moviesUiState = MutableStateFlow(SearchMoviesUiState())
    val movieUiState = _moviesUiState.asStateFlow()

    private val _tvShowUiState = MutableStateFlow(SearchTvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    private val _filterUiState = MutableStateFlow(FilterUiState())
    val filterUiState = _filterUiState.asStateFlow()

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

    fun clearFilters() {
        viewModelScope.launch {
            _filterUiState.update { FilterUiState() }
            if (selectTabIndex == 0 && _moviesUiState.value.movieName.isNotBlank()) {
                searchMovies()
            } else if (selectTabIndex == 1 && _tvShowUiState.value.tvShowName.isNotBlank()) {
                searchTvShows()
            }
        }
    }

    fun applyFilters(onDismiss: () -> Unit) {
        viewModelScope.launch {
            if (selectTabIndex == 0) {
                filterMovies()
            } else {
                filterTvShows()
            }
            onDismiss()
        }
    }

    private fun filterMovies() {
        val currentMovies = _moviesUiState.value.movies
        val rating = _filterUiState.value.selectedRating
        val genre = _filterUiState.value.selectedGenre.type

        val filteredMovies = currentMovies.filter { movie ->
            (rating <= 0.0 || movie.rating.toDoubleOrNull()?.let { it >= rating } ?: false) &&
                    (genre == GenreType.ALL || movie.genre.contains(genreToId(genre)))
        }

        _moviesUiState.update {
            it.copy(
                movies = filteredMovies,
                isLoading = false,
                searchCompleted = true
            )
        }
    }

    private fun filterTvShows() {
        val currentTvShows = _tvShowUiState.value.tvShow
        val rating = _filterUiState.value.selectedRating
        val genre = _filterUiState.value.selectedGenre.type

        val filteredTvShows = currentTvShows.filter { tvShow ->
            ( tvShow.rating.toDoubleOrNull()?.let { it >= rating } ?: false) &&
                    (genre == GenreType.ALL || tvShow.genre.contains(genreToId(genre)))
        }

        _tvShowUiState.update {
            it.copy(
                tvShow = filteredTvShows,
                isLoading = false,
                searchCompleted = true
            )
        }
    }

    private fun genreToId(genre: GenreType): Int {
        return when (genre) {
            GenreType.ALL -> 0
            GenreType.ROMANCE -> 10749
            GenreType.SCIENCE_FICTION -> 878
            GenreType.FAMILY -> 10751
            GenreType.MYSTERY -> 9648
            GenreType.HISTORY -> 36
            GenreType.WAR -> 10752
            GenreType.ACTION -> 28
            GenreType.CRIME -> 80
            GenreType.COMEDY -> 35
            GenreType.HORROR -> 27
            GenreType.WESTERN -> 37
            GenreType.MUSIC -> 10402
            GenreType.ADVENTURE -> 12
            GenreType.TV_MOVIE -> 10770
            GenreType.FANTASY -> 14
            GenreType.THRILLER -> 53
            GenreType.DRAMA -> 18
            GenreType.DOCUMENTARY -> 99
            GenreType.ANIMATION -> 16
        }
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onQuerySearchChanged(query: CharSequence) {
        if (selectTabIndex == 0) {
            _moviesUiState.update { it.copy(movieName = query.toString(), searchCompleted = false) }
        } else {
            _tvShowUiState.update {
                it.copy(
                    tvShowName = query.toString(),
                    searchCompleted = false
                )
            }
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

        _moviesUiState.update { it.copy(isLoading = true, error = null, searchCompleted = false) }

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

        _tvShowUiState.update { it.copy(isLoading = true, error = null, searchCompleted = false) }

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

    private fun onSearchTvShowsSuccess(tvShows: List<TvShowUiState>) {
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
        if (selectTabIndex == 0) {
            _moviesUiState.update {
                it.copy(
                    movieName = "",
                    movies = emptyList(),
                    isLoading = false,
                    error = null,
                    searchCompleted = false
                )
            }
        } else {
            _tvShowUiState.update {
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
