package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.entity.Media

fun Media.toUIState(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster,
//        mediaType = mediaType
    )
}
