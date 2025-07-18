package com.berlin.aflami.viewmodel.search

data class FilterItemUiState(
    val selectedRating: Float = 1f,
    val mediaGenres: List<GenreUiState> = getGenres(),
    val isLoading: Boolean = false,
)

fun getGenres(): List<GenreUiState> {
    return GenreType.entries.toTypedArray().mapIndexed { index, genre ->
        GenreUiState(
            genres = Selectable(
                type = genre,
                isSelected = genre == GenreType.ALL
            )
        )
    }
}