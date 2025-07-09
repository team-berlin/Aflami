package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.dto.MovieDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = (this.voteAverage ?: 0.0),
        releaseYear = (this.releaseDate?: Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date) as LocalDate,
        description = this.overview.orEmpty(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = this.posterPath.orEmpty()
    )
}