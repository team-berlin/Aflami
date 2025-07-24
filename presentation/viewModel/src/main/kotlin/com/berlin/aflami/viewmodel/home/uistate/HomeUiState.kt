package com.berlin.aflami.viewmodel.home.uistate

import com.berlin.aflami.viewmodel.search.GenreType
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.search.Selectable
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState


data class HomeUiState(

    val mediaUiState: List<MediaUiState> = emptyList(),
    val upcomingMovies: List<MovieUIState> = emptyList(),
    val upcomingMovieGenres: List<GenreUiState> = defaultMovieGenres,
    val isLoading: Boolean = false,
    val error: String? = null,

    )

val defaultMovieGenres = GenreType.entries.toTypedArray().mapIndexed { index, category ->
    GenreUiState(
        genres = Selectable(
            type = category,
            isSelected = index == 0,
        ),
    )
}