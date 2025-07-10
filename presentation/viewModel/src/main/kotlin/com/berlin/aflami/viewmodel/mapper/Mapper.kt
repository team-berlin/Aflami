package com.berlin.aflami.viewmodel.mapper

import android.annotation.SuppressLint
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie
import java.util.Locale

@SuppressLint("DefaultLocale")
fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        description = description,
        genre = genre,
        poster =poster
    )
}
