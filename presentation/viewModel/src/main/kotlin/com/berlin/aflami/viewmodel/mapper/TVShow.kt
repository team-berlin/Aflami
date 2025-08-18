package com.berlin.aflami.viewmodel.mapper

import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import com.berlin.entity.TVShow

fun TVShow.toUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        rating = DecimalFormat("#.#").format(rating).toString(),
        title = title,
        genre = genres.map { it.toGenreUiState() },
        releaseDate = releaseDate,
        numberOfSeasons = numberOfSeasons,
        description = description,
        duration = duration.formatRuntime(),
        companyProductionUiState = companyProductions.map { it -> it.toCompanyProductionUiState() },
        originCountry = originCountry,
        posterUrl = posterURL,
        hasVideo = hasVideo

    )
}
fun TVShow.toMediaUiState(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = java.text.DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseDate.take(4),
        genre = genres.map { it.id },
        poster = posterURL,
        companyProductionUiState = companyProductions.map { it.toCompanyProductionUiState() },
        mediaType = MediaType.TV_SHOW
    )
}