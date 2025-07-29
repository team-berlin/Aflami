package com.berlin.aflami.viewmodel.home

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
    val topRatedMediaUiState: TopRatedMediaUiState = TopRatedMediaUiState(),
    val popularMedia: PopularMediaUiState = PopularMediaUiState(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null
) {
    companion object {
        val defaultGenres = listOf(GenreUiState(-1, "All", isSelected = true))
    }
}

data class TopRatedMediaUiState(
    val topRatedMedia: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class PopularMediaUiState(
    val isLoading: Boolean = false,
    val popularMedia: List<MediaUiState> = emptyList(),
    val error: String? = null
)