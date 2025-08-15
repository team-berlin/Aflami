package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

fun Movie.toTopRateMovieEntity(): MovieHomeEntity {
    return MovieHomeEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        addedAt = System.currentTimeMillis(),
        sectionHome = SectionHome.TOP_RATING
    )
}

fun TVShow.toTopRateTVShowEntity(): TVShowHomeEntity {
    return TVShowHomeEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        addedAt = System.currentTimeMillis(),
        sectionHome = SectionHome.TOP_RATING
    )
}