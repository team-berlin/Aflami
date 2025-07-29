package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState


data class HomeUiState(
    val mediaContinueWatching: List<MediaUiState> = emptyList(),
    val upcomingMovies: List<MovieUIState> = emptyList(),
    val movieGenres: List<GenreUiState> = listOf(GenreUiState(-1, "All", isSelected = true)),
    val selectedRating: Float = 1f,
    val selectedGenres: Int = -1,
    val topRatedMediaUiState: TopRatedMediaUiState = TopRatedMediaUiState(),
    val popularMedia: PopularMediaUiState = PopularMediaUiState(),
    val moodPickerUiState: MoodPickerUiState = MoodPickerUiState(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null
)

data class TopRatedMediaUiState(
    val topRatedMedia: List<MediaUiState>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class PopularMediaUiState(
    val isLoading: Boolean = false,
    val popularMedia: List<MediaUiState> = emptyList(),
    val error: String? = null
)

data class MoodPickerUiState(
    val selectedMood: UserMoodUiState? = null,
//    val isSelectedAction: Boolean = false,
    val selectedMovie: MovieUIState = MovieUIState(),
    val movies: List<MovieUIState> = emptyList(),
    val openMovieDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null
)

data class UserMoodUiState(
    val userMood: UserMood? = null,
//    val isSelectingMood: Boolean = false,
)
