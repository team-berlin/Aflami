package com.berlin.aflami.viewmodel.mapper

import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.RatedMovie
import com.berlin.entity.RatedTVShow

fun RatedMovie.toMovieUiStateFromRated() = MovieUiState(
    id = id,
    title = title,
    posterUrl = posterUrl ?: "",
    rating = DecimalFormat("#.#").format(userRating).toString(),
)

fun RatedTVShow.toTvUiStateFromRated() = TVShowUiState(
    id = id,
    title = name,
    posterUrl = posterUrl ?: "",
    rating = DecimalFormat("#.#").format(userRating).toString(),
)