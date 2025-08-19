package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.HomeSection
import com.berlin.repository.datasource.local.dto.HomeTVShowEntity

fun Movie.toPopularMovieEntity(): HomeMovieEntity {
    return HomeMovieEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        homeSection = HomeSection.POPULAR
    )
}

fun TVShow.toPopularTVShowEntity(): HomeTVShowEntity {
    return HomeTVShowEntity(
        id = id,
        title = title,
        rating = rating.toString(),
        releaseYear = releaseDate,
        genre = genres.map { it.id },
        poster = posterURL,
        homeSection = HomeSection.POPULAR
    )
}
