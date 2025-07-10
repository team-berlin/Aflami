package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.uistate.TvShowUiState
import com.berlin.entity.TvShow

fun TvShow.toUiState(): TvShowUiState {
    return TvShowUiState(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseYear.toString(),
        description = description,
        genre = genre,
        poster =poster
    )
}