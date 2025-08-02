package com.berlin.aflami.viewmodel.home

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState


@Immutable
data class HomeScreenState(
    val popularMediaUiState: PopularMediaUiState = PopularMediaUiState(),
    val continueWatchingUiState: ContinueWatchingUiState = ContinueWatchingUiState(),
    val topRatedMediaUiState: TopRatedMediaUiState = TopRatedMediaUiState(),
    val selectedRating: Float = 1f,
    val selectedGenres: Int = -1,
    val moodPickerUiState: MoodPickerUiState = MoodPickerUiState(),
    val upcomingMoviesUiState: UpcomingMoviesUiState = UpcomingMoviesUiState(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
)

@Immutable
data class PopularMediaUiState(
    val popularMedia: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

@Immutable
data class ContinueWatchingUiState(
    val continueWatchingMediaList: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

data class TopRatedMediaUiState(
    val topRatedMedia: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

@Immutable
data class UpcomingMoviesUiState(
    val upcomingMovies: List<MovieUIState> = emptyList(),
    val movieGenres: List<GenreUiState> = listOf(GenreUiState(-1, "All", isSelected = true)),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@Immutable
data class MoodPickerUiState(
    val selectedMood: UserMoodUiState? = null,
    val selectedMovie: MovieUIState = MovieUIState(),
    val movies: List<MovieUIState> = emptyList(),
    val openMovieDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
)

@Immutable
data class UserMoodUiState(
    val userMood: UserMood? = null,
)
