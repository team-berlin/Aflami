package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.search.GenreType
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.search.Selectable
import com.berlin.aflami.viewmodel.uistate.MovieUIState

data class HomeUiState(
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
