package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.entity.Movie
import java.text.DecimalFormat

fun Movie.toMovieUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseDate.take(4),
        genre = genres,
        posterUrl = posterURL
    )
}
//fun Movie.toUIStateMedia(): MediaUiState {
//    return MediaUiState(
//        id = id,
//        title = title,
//        rating = DecimalFormat("#.#").format(rating).toString(),
//        releaseYear = releaseDate.take(4),
//        genre = genres,
//        poster = posterURL,
//        mediaType =MediaType.MOVIE
//    )
//}