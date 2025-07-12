package com.berlin.aflami.viewmodel.uistate

data class FilterUiState (
    val selectedRating: Float = 1f,
    val selectedGenre: GenreUiState = GenreUiState(),
)
data class GenreUiState(
    val type: GenreType = GenreType.ALL,
    val isSelected: Boolean = true
)

enum class GenreType {
    ALL,
    ROMANCE,
    SCIENCE_FICTION,
    FAMILY,
    MYSTERY,
    HISTORY,
    WAR,
    ACTION,
    CRIME,
    COMEDY,
    HORROR,
    WESTERN,
    MUSIC,
    ADVENTURE,
    TV_MOVIE,
    FANTASY,
    THRILLER,
    DRAMA,
    DOCUMENTARY,
    ANIMATION
}