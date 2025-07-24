package com.berlin.aflami.viewmodel.search

data class GenreUiState(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

data class FilterMediaSelected(
    val selectedRating: Float = 1f,
    val selectedGenres: Int = -1,
)

data class FilterItemUiState(
    val filterTvShowSelected: FilterMediaSelected = FilterMediaSelected(),
    val filterMovieSelected: FilterMediaSelected = FilterMediaSelected(),
    val genreUiStates: List<GenreUiState> = defaultGenres,
    val isLoading: Boolean = false
) {
    companion object {
        val defaultGenres = listOf(GenreUiState(-1, "All", isSelected = true))
    }
}