package com.berlin.aflami.viewmodel.mapper

import android.annotation.SuppressLint
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie

@SuppressLint("DefaultLocale")
fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = String.format("%.1f", rating),
        releaseYear = releaseYear.year.toString(),
        description = description,
        genre = genre,
        poster =poster
    )
}
