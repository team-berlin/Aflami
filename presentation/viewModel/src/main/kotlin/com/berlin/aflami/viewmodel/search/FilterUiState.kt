package com.berlin.aflami.viewmodel.search

data class GenreUiState(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

data class FilterTabSelected(
    val selectedRating: Float = 1f,
    val selectedGenres: Int = -1,
    val genreType: TabOption? = null
)

data class FilterItemUiState(
    val filterTabSelected: FilterTabSelected = FilterTabSelected(),
    val genreUiStates: List<GenreUiState> = defaultGenres,
    val isLoading: Boolean = false
) {
    companion object {
        val defaultGenres = listOf(GenreUiState(-1, "All", isSelected = true))
    }
}