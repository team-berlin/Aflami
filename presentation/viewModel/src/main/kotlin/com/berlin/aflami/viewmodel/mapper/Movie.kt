package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.toDomain
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import com.berlin.entity.Movie
import java.text.DecimalFormat

fun Movie.toMovieUiState(): MovieUiState {
    return MovieUiState(
        id = id,
        title = title,
        rating = DecimalFormat("#.#").format(rating).toString(),
        releaseDate = releaseDate.take(4),
        genre = genres.map { it.toGenreUiState() },
        posterUrl = posterURL,
        description = description,
        duration = duration.formatRuntime(),
        companyProductionUiState = companyProductions.map { it -> it.toCompanyProductionUiState() },
        originCountry = originCountry,
        hasVideo = hasVideo
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

fun MovieUiState.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        rating = rating.toDoubleOrNull() ?: 0.0,
        releaseDate = releaseDate,
        posterURL = posterUrl,
        screenShot = posterUrl,
        description = description,
        genres = genre.map { it.toDomain() },
        duration = duration.parseRuntime(),
        hasVideo = hasVideo,
        companyProductions = emptyList(),
        originCountry = originCountry,
        galleryUrl = emptyList(),
        reviews = emptyList(),
        isFavourite = false
    )
}
fun Int.formatRuntime(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return "${hours}h ${remainingMinutes}m"
}

fun String.parseRuntime(): Int =
    Regex("(\\d+)([hm])").findAll(this)
        .sumOf {
            val (value, unit) = it.destructured
            if (unit == "h") value.toInt() * 60 else value.toInt()
        }