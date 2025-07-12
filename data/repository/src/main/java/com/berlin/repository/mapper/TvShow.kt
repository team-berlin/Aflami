package com.berlin.repository.mapper

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.util.toLocalDate


fun TVShowDto.toDomain(): TVShow {
    return TVShow(
        id = this.id?.toLong() ?: 0L,
        title = this.name.orEmpty(),
        rating = this.voteAverage?.toDouble() ?: 0.0,
        releaseYear = (((this.firstAirDate ?: "")).toLocalDate()),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "https://image.tmdb.org/t/p/w500${this.posterPath.orEmpty()}",
    )
}