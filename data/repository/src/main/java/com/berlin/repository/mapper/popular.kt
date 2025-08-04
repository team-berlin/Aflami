package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

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


fun Movie.toPopularMovieEntity(): MovieHomeEntity {
    return MovieHomeEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        addedAt = System.currentTimeMillis()
    )
}

fun TVShow.toPopularTVShowEntity(): TVShowHomeEntity {
    return TVShowHomeEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        addedAt = System.currentTimeMillis()
    )
}
