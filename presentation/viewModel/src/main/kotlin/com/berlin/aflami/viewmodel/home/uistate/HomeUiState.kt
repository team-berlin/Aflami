package com.berlin.aflami.viewmodel.home.uistate

import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState


data class HomeUiState(

    val mediaContinueWatching: List<MediaUiState> = emptyList(),
    val upcomingMovies: List<MovieUIState> = emptyList(),
    val upcomingMovieGenres: List<GenreUiState> = defaultGenres,
    val selectedRating: Float = 1f,
    val selectedGenres: Int = -1,
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null
) {
    companion object {
        val defaultGenres = listOf(GenreUiState(-1, "All", isSelected = true))
    }
}

