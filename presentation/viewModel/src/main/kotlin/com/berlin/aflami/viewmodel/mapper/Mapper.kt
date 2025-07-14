package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow

fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre.map { it.name },
        poster =poster
    )
}

fun Movie.toUiModel(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        mediaType = MediaType.MOVIE,
        poster = poster,
        backdrop = backdropPath,
        overview = overview.orEmpty(),
        genre = genre.map { it.name } ,
        runtime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "",
        releaseYear = releaseYear.year.toString(),
        country ="",
        isFavorite =false,
        showReadMore =false,
    )
}

fun TVShow.toUiModel() = MediaUiState(
    id = id,
    title = title,
    mediaType = MediaType.TV_SHOW,
    poster = poster,
    backdrop = backdropPath,
    overview = overview.orEmpty(),
    genre = genre.map { it.name } ,
)
