package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import java.text.DecimalFormat

fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster
    )
}


fun Movie.toUIStateMedia(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster
    )
}