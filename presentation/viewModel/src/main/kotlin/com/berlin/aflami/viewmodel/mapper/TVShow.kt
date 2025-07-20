package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.TVShow

fun TVShow.toUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseYear.toString(),
        genre = genre,
        poster = poster
    )
}

fun TVShow.toUIStateMedia(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseYear.toString(),
        genre = genre,
        poster = poster
    )
}