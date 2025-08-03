package com.berlin.aflami.viewmodel.mapper

import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.TVShow

fun TVShow.tvShowToUiState(): TVShowUiState {
    return TVShowUiState(
        id = id,
        hasVideo = hasVideo,
        rating = DecimalFormat("#.#").format(rating).toString(),
        title = title,
        genre = genres,
        releaseDate = releaseDate,
        numberOfSeasons = numberOfSeasons,
        description = description,
        duration = duration,
        companyProductionUiState = companyProductions.map { it -> it.toCompanyProductionUiState() },
        originCountry = originCountry,
        posterUrl = posterURL,
    )
}