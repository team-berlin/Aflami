package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.search.parseRating
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import com.berlin.entity.TVShow
import java.text.DecimalFormat

fun TVShow.toUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        rating = parseRating(DecimalFormat("#.#").format(rating).toString().replace('٫', '.')) ,
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
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        companyProductionUiState = companyProductions.map { it.toCompanyProductionUiState() },
        mediaType = MediaType.TV_SHOW
    )
}