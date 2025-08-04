package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

fun TVShowHomeEntity.toDomain(): TVShow {
    return TVShow(
        id = id,
        title = title,
        rating = rating.toDoubleOrNull() ?: 0.0,
        posterURL = poster,
        releaseDate = releaseYear,
        screenShot = "",
        description = "",
        genres = genre.map { Genre(id = it, name = "") },
        duration = 0,
        hasVideo = false,
        companyProductions = emptyList(),
        originCountry = "",
        numberOfSeasons = 0,
        galleryUrl = emptyList()
    )
}

