package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaUiState
import com.berlin.entity.Media
import com.berlin.entity.Movie
import com.berlin.entity.TVShow

fun Media.toUIState(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster,
        mediaType = mediaType
    )
}
