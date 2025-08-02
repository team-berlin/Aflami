package com.berlin.aflami.viewmodel.mapper

import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.TVShow

fun TVShow.tvShowToUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseDate.take(4),
        genre = genres,
        poster = posterURL
    )
}
//fun TVShow.toUIStateMedia(): MediaUiState {
//    return MediaUiState(
//        id = id,
//        title = title,
//        rating = DecimalFormat("#.#").format(rating).toString(),
//        releaseYear = releaseDate.take(4),
//        genre = genres,
//        poster = posterURL,
//        mediaType = MediaType.TVSHOW
//    )
//}