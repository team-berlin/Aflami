package com.berlin.aflami.viewmodel.search

data class FilterItemUiState(
    val selectedRating: Float = 1f,
    val selectedGenre: GenreType? = null,
    val mediaGenres: List<GenreUiState> = defaultGenres,
    val isLoading: Boolean = false,
) {

    companion object {
        val defaultGenres = GenreType.entries.toTypedArray().mapIndexed { index, genre ->
            GenreUiState(
                genres = Selectable(
                    type = genre, isSelected = index == 0
                )
            )
        }
    }
}