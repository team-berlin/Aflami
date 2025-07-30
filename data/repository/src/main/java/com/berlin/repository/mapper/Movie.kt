package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.entity.ProductionCompany
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.ProductionCompany
import kotlinx.datetime.LocalDate
import java.time.Instant

fun SearchingEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = stringToLocalDate(releaseYear),
        genres = this.genre,
        poster = this.poster
    )
}

fun MovieDto.toLocal(query: String, type: String, page: Int, mediaType: String): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        time = Instant.now().epochSecond,
        id = this.id?.toLong() ?: 0L,
        title = this.title ?: "",
        rating = this.voteAverage ?: 0.0,
        releaseYear = (releaseDate ?: ""),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        page = page,
        mediaType = mediaType
    )
}

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = (this.voteAverage ?: 0.0),
        releaseDate = stringToLocalDate(releaseDate ?: ""),
        genres = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        overview = this.overview.orEmpty(),
        posterUrl = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        backdropUrl = "$BACKDROP_PREFIX${this.backdropPath.orEmpty()}",
        releaseDate = this.releaseDate.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        runtime = this.runtime ?: 0,
        genres = this.genres?.map { it.toEntity() } ?: emptyList(),
        productionCompanies = this.productionCompanies?.map { company ->
            company.toEntity()
        } ?: emptyList(),
        hasVideo = this.video,
        originCountry = this.originCountry?.get(0),
        duration = this.runtime.formatRuntime())
}

fun MovieDto.toDomain(mediaType: String): Media {
    return Media(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        releaseYear = stringToLocalDate(releaseDate ?: ""),
        mediaType = mediaType,
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}

fun stringToLocalDate(dateString: String): LocalDate {
    return runCatching {
        LocalDate.parse(dateString)
    }.getOrElse { LocalDate.parse("1960-01-01") }
}

fun GenreDto.toEntity() = GenreEntity(
    id = this.id ?: 0, name = this.name.orEmpty()
)

fun ProductionCompany.toEntity() = com.berlin.entity.ProductionCompany(
    id = this.id ?: 0,
    name = this.name.orEmpty(),
    poster = this.logoPath?.let { "$POSTER_PREFIX$it" },
    originCountry = this.originCountry.orEmpty()
)

fun Int?.formatRuntime(): String? {
    if (this == null || this == 0) return null
    val hours = this / 60
    val remainingMinutes = this % 60
    return "${hours}h ${remainingMinutes}m"
}

const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"
const val BACKDROP_PREFIX = "https://image.tmdb.org/t/p/original"
