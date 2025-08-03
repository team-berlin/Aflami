package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import java.text.DecimalFormat

fun Movie.toMovieUiState(): MovieUiState {
    return MovieUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseDate = releaseDate.take(4),
        genre = genres,
        posterUrl = posterURL,
        description = description,
        duration = duration.toString(),
        companyProductionUiState = companyProductions.map { it -> it.toCompanyProductionUiState() },
        originCountry = originCountry,
    )
}

fun Movie.toMediaUiState(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseDate.take(4),
        genre = genres.map { it.id },
        poster = posterURL,
        companyProductionUiState = companyProductions.map { it.toCompanyProductionUiState() },
        mediaType = MediaType.MOVIE
    )
}

fun TVShow.toMediaUiState(): MediaUiState {
    return MediaUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseYear = releaseDate.take(4),
        genre = genres.map { it.id },
        poster = posterURL,
        companyProductionUiState = companyProductions.map { it.toCompanyProductionUiState() },
        mediaType = MediaType.TV_SHOW
    )
}