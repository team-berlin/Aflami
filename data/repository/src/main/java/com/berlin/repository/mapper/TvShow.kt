package com.berlin.repository.mapper

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.util.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun TVShowDto.toDomain(): TVShow {
    return TVShow(
        id = this.id?.toLong() ?: 0L,
        title = this.name.orEmpty(),
        rating = this.voteAverage?.toFloat() ?: 0.0f,
        releaseYear = (((this.firstAirDate ?: "")).toLocalDate()).toString(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster ="https://image.tmdb.org/t/p/w500${this.posterPath.orEmpty()}",
    )
}