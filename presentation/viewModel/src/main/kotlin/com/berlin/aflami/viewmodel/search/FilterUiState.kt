package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.search.FilterItemUiState.Companion.defaultGenres

data class GenreUiState(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

data class FilterMediaSelected(
    val selectedRating: Float = 0f,
    val selectedGenres: Int = -1,
    val genreUiStates: List<GenreUiState> = defaultGenres,

    )

data class FilterItemUiState(
    val filterTvShowSelected: FilterMediaSelected = FilterMediaSelected(),
    val filterMovieSelected: FilterMediaSelected = FilterMediaSelected(),
    val isLoading: Boolean = false
) {
    companion object {
        val defaultGenres = listOf(GenreUiState(-1, "All", isSelected = true))
    }
}