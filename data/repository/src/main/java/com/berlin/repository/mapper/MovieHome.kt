package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.dto.MovieHomeEntity

fun MovieHomeEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        rating = rating.toDoubleOrNull() ?: 0.0,
        releaseDate = releaseYear,
        posterURL = poster,
        screenShot = "",
        description = "",
        genres = genre.map { Genre(id = it, name = "") },
        duration = 0,
        hasVideo = false,
        companyProductions = emptyList(),
        originCountry = "",
        galleryUrl = emptyList(),
        reviews = emptyList()
    )
}