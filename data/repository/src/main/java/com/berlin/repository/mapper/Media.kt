package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity


fun ContinueWatchingMovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = this.reviews
    )
}

fun ContinueWatchingTVShowEntity.toTVShow(): TVShow {
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        seasons = this.seasons,
        reviews = this.reviews,
    )
}

fun Movie.toLocalEntity(): ContinueWatchingMovieEntity {
    return ContinueWatchingMovieEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = this.reviews,
    )
}

fun TVShow.toLocalEntity(): ContinueWatchingTVShowEntity {
    return ContinueWatchingTVShowEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        seasons = this.seasons,
        reviews = this.reviews,
    )
}
