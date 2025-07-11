package com.berlin.aflami.viewmodel.mapper

import android.annotation.SuppressLint
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie

@SuppressLint("DefaultLocale")
fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        description = description,
        //genre = genre,
        poster = poster
    )
}

fun MediaTypeEntity.toUiState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseYear.toString(),
        // genre = genre,
        poster = "https://image.tmdb.org/t/p/w500${this.poster.orEmpty()}"
    )
}
