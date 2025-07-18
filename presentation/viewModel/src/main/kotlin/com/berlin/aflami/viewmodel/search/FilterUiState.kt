package com.berlin.aflami.viewmodel.search

data class FilterItemUiState(
    val selectedRating: Float = 1f,
    val mediaGenres: List<GenreUiState> = defaultGenres,
    val isLoading: Boolean = false,
) {
    val hasFilterData: Boolean
        get() = selectedRating > 0 || mediaGenres.any { it.genres.isSelected }

    companion object {
        val defaultGenres = GenreType.entries.toTypedArray().mapIndexed { index, genre ->
            GenreUiState(
                genres = Selectable(
                    type = genre, isSelected = index == 0
                )
            )
        }

        fun default() = FilterItemUiState(
            selectedRating = 1f, mediaGenres = defaultGenres, isLoading = false
        )
    }
}