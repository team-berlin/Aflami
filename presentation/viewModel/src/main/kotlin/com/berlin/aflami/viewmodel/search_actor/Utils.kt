package com.berlin.aflami.viewmodel.search_actor

data class FilterUiState(
    val selectedRating: Float = 1f,
    val selectedGenre: GenreUiState = GenreUiState(),
)

data class GenreUiState(
    val type: GenreType = GenreType.ALL,
    val isSelected: Boolean = true
)

enum class GenreType() {
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

 fun genreToId(genre: GenreType): Int {
    return when (genre) {
        GenreType.ALL -> 0
        GenreType.ROMANCE -> 10749
        GenreType.SCIENCE_FICTION -> 878
        GenreType.FAMILY -> 10751
        GenreType.MYSTERY -> 9648
        GenreType.HISTORY -> 36
        GenreType.WAR -> 10752
        GenreType.ACTION -> 28
        GenreType.CRIME -> 80
        GenreType.COMEDY -> 35
        GenreType.HORROR -> 27
        GenreType.WESTERN -> 37
        GenreType.MUSIC -> 10402
        GenreType.ADVENTURE -> 12
        GenreType.TV_MOVIE -> 10770
        GenreType.FANTASY -> 14
        GenreType.THRILLER -> 53
        GenreType.DRAMA -> 18
        GenreType.DOCUMENTARY -> 99
        GenreType.ANIMATION -> 16
    }
}