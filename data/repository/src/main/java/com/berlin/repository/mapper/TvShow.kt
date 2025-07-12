package com.berlin.repository.mapper

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.dto.TvShowDto
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun TvShowDto.toDomain(): TVShow {
    return TVShow(
        id = this.id?.toLong() ?: 0L,
        title = this.name.orEmpty(),
        rating = (this.voteAverage ?: 0.0),
        releaseYear = (this.firstAirDate?: Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date) as LocalDate,
        description = this.overview.orEmpty(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = this.posterPath.orEmpty()
    )

}