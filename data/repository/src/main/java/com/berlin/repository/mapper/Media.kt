package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.MediaType
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import java.time.Instant



fun GenreDto.toDomain(): Genre {
    return Genre(id = this.id?:-1, name = this.name?:"All")
}

fun ContinueWatchingMovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseYear,
        genres = emptyList(),
        posterURL = this.posterUrl,
        screenShot = "",
        description = TODO(),
        duration = TODO(),
        hasVideo = TODO(),
        productionCompanies = TODO(),
        originCountry = TODO(),
        galleryUrl = TODO(),
    )
}

fun ContinueWatchingTVShowEntity.toTVShow(): TVShow {
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        posterURL = TODO(),
        releaseDate = TODO(),
        screenShot = TODO(),
        description = TODO(),
        genres = TODO(),
        duration = TODO(),
        hasVideo = TODO(),
        productionCompanies = TODO(),
        originCountry = TODO(),
        seasons = TODO(),
        galleryUrl = TODO(),
        reviews = TODO(),
    )
}

fun Movie.toLocalEntity(): ContinueWatchingMovieEntity {
    return ContinueWatchingMovieEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = this.releaseDate.toString(),
        posterUrl = this.posterURL,
        typeOfMedia = MediaType.MOVIE
    )
}

fun TVShow.toLocalEntity(): ContinueWatchingTVShowEntity {
    return ContinueWatchingTVShowEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = this.releaseDate.toString(),
        posterUrl = this.posterURL,
        typeOfMedia = MediaType.TVSHOW
    )
}
