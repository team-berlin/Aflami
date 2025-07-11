package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie

fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster =poster
    )
}
