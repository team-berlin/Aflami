package com.berlin.aflami.viewmodel.search

import com.berlin.entity.MovieGenre

data class GenreUiState(
    val genres: Selectable<GenreType> = Selectable(
        type = GenreType.ALL, isSelected = false
    ),
)

data class Selectable<T>(
    val isSelected: Boolean = true, val type: T
)

fun GenreType.toGenreType(): Int {
    return genreToId(this)
}

fun GenreType.toGenreMovieType(): MovieGenre {
    return when (this) {
        GenreType.ALL -> MovieGenre.ALL
        GenreType.ROMANCE -> MovieGenre.ROMANCE
        GenreType.SCIENCE_FICTION -> MovieGenre.SCIENCE_FICTION
        GenreType.FAMILY -> MovieGenre.FAMILY
        GenreType.MYSTERY -> MovieGenre.MYSTERY
        GenreType.HISTORY -> MovieGenre.HISTORY
        GenreType.WAR -> MovieGenre.WAR
        GenreType.ACTION -> MovieGenre.ACTION
        GenreType.CRIME -> MovieGenre.CRIME
        GenreType.COMEDY -> MovieGenre.COMEDY
        GenreType.HORROR -> MovieGenre.HORROR
        GenreType.WESTERN -> MovieGenre.WESTERN
        GenreType.MUSIC -> MovieGenre.MUSIC
        GenreType.ADVENTURE -> MovieGenre.ADVENTURE
        GenreType.TV_MOVIE -> MovieGenre.TV_MOVIE
        GenreType.FANTASY -> MovieGenre.FANTASY
        GenreType.THRILLER -> MovieGenre.THRILLER
        GenreType.DRAMA -> MovieGenre.DRAMA
        GenreType.DOCUMENTARY -> MovieGenre.DOCUMENTARY
        GenreType.ANIMATION -> MovieGenre.ANIMATION
    }
}

enum class GenreType {
    ALL, ROMANCE, SCIENCE_FICTION, FAMILY, MYSTERY, HISTORY, WAR, ACTION, CRIME, COMEDY, HORROR, WESTERN, MUSIC, ADVENTURE, TV_MOVIE, FANTASY, THRILLER, DRAMA, DOCUMENTARY, ANIMATION
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

fun List<GenreUiState>.selectByMovieGenre(movieGenre: GenreType): List<GenreUiState> {
    return this.map { movies ->
        movies.copy(
            genres = Selectable(
                type = movies.genres.type, isSelected = movies.genres.type == movieGenre
            )
        )
    }
}