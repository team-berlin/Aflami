package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.ProductionCompany
import com.berlin.entity.Review
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.MovieEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.ProductionCompanyDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto


fun MovieDetailsDto.toDomain(
     reviews: List<Review> =emptyList(),
     galleryImages: List<String> = emptyList()
): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = (this.voteAverage ?: 0.0),
        releaseDate = this.releaseDate ?: "10-12-2014",
        genres = this.genres?.map{it.toDomain() }?:emptyList(),
        posterURL = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        screenShot = this.backdropPath?:"",
        description = this.overview?:"Description not available",
        duration = this.runtime ?: 0,
        hasVideo = this.video == true,
        productionCompanies = this.productionCompanies?.map {
            it.toDomain()
        } ?: emptyList(),
        originCountry = this.originCountry?.firstOrNull() ?: "",
        galleryUrl =galleryImages ,
        reviews = reviews
    )
}

fun Movie.toLocal(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = emptyList(),
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = emptyList(),
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = emptyList()
    )
}

fun Movie.toRecentMovieEntity(): RecentlyWatchedMovieEntity {
    return RecentlyWatchedMovieEntity(
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

fun RecentlyWatchedMovieEntity.toDomain(): Movie {
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

fun GenreDto.toDomain() = Genre(
    id = this.id ?: 0, name = this.name.orEmpty()
)
fun MovieEntity.toDomain(): Movie {
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
        reviews = this.reviews,
    )
}


 fun ProductionCompanyDto.toDomain() = ProductionCompany(
    id = this.id ?: 0,
    name = this.name.orEmpty(),
    posterURL = this.logoPath?.let { "$POSTER_PREFIX$it" } ?: "",
    originCountry = this.originCountry?:"",
)

const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"
const val BACKDROP_PREFIX = "https://image.tmdb.org/t/p/original"
