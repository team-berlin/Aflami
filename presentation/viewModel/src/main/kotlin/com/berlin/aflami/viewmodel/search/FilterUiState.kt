package com.berlin.aflami.viewmodel.search

import androidx.compose.runtime.Immutable

@Immutable
data class GenreUiState(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false,
)

@Immutable
data class FilterMediaSelected(
    val selectedRating: Float = 0f,
    val selectedGenres: Int = -1,
    val genreUiStates: List<GenreUiState> = listOf(GenreUiState(-1, "All", isSelected = true)),
)

@Immutable
data class FilterItemUiState(
    val filterTvShowSelected: FilterMediaSelected = FilterMediaSelected(),
    val filterMovieSelected: FilterMediaSelected = FilterMediaSelected(),
    val isLoading: Boolean = false,
)