package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.MovieDto
import kotlinx.datetime.LocalDate
import java.time.Instant
import java.time.format.DateTimeFormatter

const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"

fun SearchingEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = stringToLocalDate(releaseYear),
        genre = this.genre,
        poster = this.poster
    )
}

fun MovieDto.toLocal(query: String, type: String, page: Int,mediaType:String): SearchingEntity {
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

private fun stringToLocalDate(dateString: String): LocalDate {
    return runCatching {
        LocalDate.parse(dateString)
    }.getOrElse { LocalDate.parse("1960-01-01") } // TODO:
}
