package com.berlin.repository.mapper

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.util.toLocalDate

fun SearchingEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = releaseYear.toLocalDate(),
        genre = this.genre,
        poster = this.poster
    )
}

fun MovieDto.toLocal(query: String, time: Long, type: String): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        time = time,
        id = this.id?.toLong() ?: 0L,
        title = this.title ?: "",
        rating = this.voteAverage ?: 0.0,
        releaseYear = releaseDate ?: "",
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}

const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"