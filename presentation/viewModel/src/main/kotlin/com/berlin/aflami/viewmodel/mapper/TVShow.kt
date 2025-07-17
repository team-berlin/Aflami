package com.berlin.aflami.viewmodel.mapper

import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import com.berlin.entity.TVShow

fun TVShow.toUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseYear.toString(),
        genre = genre,
        poster = poster
    )
}