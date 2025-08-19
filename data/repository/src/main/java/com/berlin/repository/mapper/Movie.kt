package com.berlin.repository.mapper

import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.repository.datasource.local.dto.MovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.ProductionCompanyDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.tmdbImageUrl


fun MovieDetailsDto.toDomain(
     reviews: List<Review> =emptyList(),
     galleryImages: List<String> = emptyList()
): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = (this.voteAverage ?: 0.0),
        releaseDate = this.releaseDate.orEmpty(),
        genres = this.genres?.map { it.toDomain() } ?: genresId?.map { it.toDomainGenre() }
            .orEmpty(),
        posterURL = tmdbImageUrl(posterPath, MediaUrls.TmdbImageSize.W500).orEmpty(),
        screenShot = this.backdropPath ?: "",
        description = this.overview ?: "Description not available",
        duration = this.runtime ?: 0,
        hasVideo = this.video == true,
        companyProductions = this.productionCompanies?.map {
            it.toDomain()
        } .orEmpty(),
        originCountry = this.originCountry?.firstOrNull() ?: "",
        galleryUrl = galleryImages,
        reviews = reviews,
        isFavourite = true
    )
}


fun Movie.toRecentMovieEntity(): RecentlyWatchedMovieEntity {
    return RecentlyWatchedMovieEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres.map { it.id },
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = emptyList(),
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = emptyList(),
    )
}

fun RecentlyWatchedMovieEntity.toDomain(): Movie {
    return Movie(
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
        companyProductions = emptyList(),
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = emptyList(),
        isFavourite = false,
    )
}

fun GenreDto.toDomain() = Genre(
    id = this.id ?: 0, name = this.name.orEmpty()
)
fun Int.toDomainGenre() = Genre(
    id = this, name = ""
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
        companyProductions = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = this.reviews,
        isFavourite = false,
    )
}


 fun ProductionCompanyDto.toDomain() = CompanyProduction(
    id = this.id ?: 0,
    name = this.name ?: "",
    posterURL = tmdbImageUrl(this.logoPath, MediaUrls.TmdbImageSize.W185) ?: "",
    originCountry = this.originCountry?:"",
)
