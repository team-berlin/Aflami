package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.TVShow

fun TVShow.toUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseYear?.year.toString(),
        genre = genre,
        poster = poster
    )
}

fun TVShow.toUIStateMedia(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseYear?.year.toString(),
        genre = genre,
        poster = poster,
        mediaType = MediaType.TVSHOW
    )
}