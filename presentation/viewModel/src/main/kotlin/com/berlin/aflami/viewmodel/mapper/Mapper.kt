package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaScreenState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow

fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster =poster
    )
}

fun Movie.toUiModel(): MediaScreenState {
    return MediaScreenState(
        id = id,
        title = title,
        mediaType = MediaType.MOVIE,
        poster = poster,
        backdrop = backdropPath,
        overview = overview.orEmpty(),
        genre = genre ,
        mediaDuration = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "",
        releaseYear = releaseYear.year.toString(),
        country ="",
        isFavorite =false,
        isOverviewExpanded =false,
    )
}

fun TVShow.toUiModel() = MediaScreenState(
    id = id,
    title = title,
    mediaType = MediaType.TV_SHOW,
    poster = poster,
    backdrop = backdropPath,
    overview = overview.orEmpty(),
    genre = genre ,
)
