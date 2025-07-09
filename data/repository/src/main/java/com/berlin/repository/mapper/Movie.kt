package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.dto.MovieDto
import kotlinx.datetime.LocalDate

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        releaseYear = releaseDate?.let { LocalDate.parse(it) } ?: LocalDate(1900, 1, 1),
        description = this.overview.orEmpty(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "https://image.tmdb.org/t/p/w500${this.posterPath.orEmpty()}"
    )
}