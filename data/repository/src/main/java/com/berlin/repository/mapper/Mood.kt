package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.HomeSection

fun Movie.toMovieByMoodEntity(): HomeMovieEntity {
    return HomeMovieEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        homeSection = HomeSection.BY_MOOD
    )
}
