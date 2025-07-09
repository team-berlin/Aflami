package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.dto.MovieDto

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id?.toLong() ?: 0L,
        title = this.title.orEmpty(),
        rating = (this.voteAverage ?: 0.0).toFloat(),
        releaseYear = this.releaseDate?: "",
        description = this.overview.orEmpty(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = this.posterPath.orEmpty()
    )
}