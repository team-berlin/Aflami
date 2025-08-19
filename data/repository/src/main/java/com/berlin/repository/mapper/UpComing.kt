package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.HomeSection

fun Movie.toUpComingMovieEntity(genreId: Long?): HomeMovieEntity {
    return HomeMovieEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = listOf(genreId?.toInt()?:-1),
        poster = posterURL,
        homeSection = HomeSection.UPCOMING
    )
}