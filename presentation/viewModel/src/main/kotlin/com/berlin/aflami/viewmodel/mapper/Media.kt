package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.util.MediaType
import com.berlin.entity.Media

fun Media.toUIState(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster,
        mediaType = MediaType.valueOf(mediaType)
    )
}
//fun Media.toUIState(type: MediaType = MediaType.MOVIE): MediaUiState {
//    return MediaUiState(
//        id = id,
//        title = title,
//        rating = rating.toString().take(3),
//        releaseYear = releaseYear.year.toString(),
//        genre = genre,
//        poster = poster,
//        mediaType = type
//    )
//}