package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.MediaType
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

fun Movie.toUpComingMovieEntity(): MovieHomeEntity {
    return MovieHomeEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        addedAt = System.currentTimeMillis(),
        type = MediaType.UPCOMING
    )
}