package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.RatedMovie
import com.berlin.entity.RatedTVShow

fun RatedMovie.toMovieUiStateFromRated() = MovieUiState(
    id = id,
    title = title,
    posterUrl = posterUrl ?: "",
    rating = userRating.toString(),
)

fun RatedTVShow.toTvUiStateFromRated() = TVShowUiState(
    id = id,
    title = name,
    posterUrl = posterUrl ?: "",
    rating = userRating.toString(),
)