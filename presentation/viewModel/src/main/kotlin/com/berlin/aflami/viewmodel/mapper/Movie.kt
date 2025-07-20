package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.uistate.MediaUiState
import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow

fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster,
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

fun Movie.toUIStateMedia(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster
    )
}