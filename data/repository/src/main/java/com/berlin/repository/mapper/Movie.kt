package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.CompanyProduction
import com.berlin.repository.datasource.local.dto.MovieEntity
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDto
import kotlinx.datetime.LocalDate
import java.time.Instant

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
        galleryUrl =this.galleryUrl,
        reviews = this.reviews,
    )
}

fun MovieDto.toLocal(query: String, type: String, page: Int, mediaType: String): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        timeStamp = Instant.now().toEpochMilli(),
        queryType = QueryType.MOVIE,
    )
}

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = (this.voteAverage ?: 0.0),
        releaseDate = this.releaseDate,
        genres = this.genreIds?.filterNotNull() ?: emptyList(),
        posterURL = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        screenShot = TODO(),
        description = TODO(),
        duration = TODO(),
        hasVideo = TODO(),
        companyProductions = TODO(),
        originCountry = TODO(),
        galleryUrl = TODO()
    )
}

fun MovieDetailsDto.toDomain(): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        description = this.overview.orEmpty(),
        posterURL = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        screenShot = "$BACKDROP_PREFIX${this.backdropPath.orEmpty()}",
        releaseDate = this.releaseDate.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        duration = this.runtime ?: 0,
        genres = this.genres?.map { it.toEntity() } ?: emptyList(),
        companyProductions = this.productionCompanies?.map { company ->
            company.toEntity()
        } ?: emptyList(),
        hasVideo = this.video,
        originCountry = this.originCountry?.get(0),
       // galleryUrl =
        )
}


}

fun stringToLocalDate(dateString: String): LocalDate {
    return runCatching {
        LocalDate.parse(dateString)
    }.getOrElse { LocalDate.parse("1960-01-01") }
}

fun GenreDto.toEntity() = Genre(
    id = this.id ?: 0, name = this.name.orEmpty()
)

fun CompanyProduction.toEntity() = CompanyProduction(
    id = this.id ?: 0,
    name = this.name.orEmpty(),
    poster = this.logoPath?.let { "$POSTER_PREFIX$it" },
    originCountry = this.originCountry.orEmpty()
)



const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"
const val BACKDROP_PREFIX = "https://image.tmdb.org/t/p/original"
