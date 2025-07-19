package com.berlin.repository.mapper

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.TVShowDto
import kotlinx.datetime.toLocalDate
import java.time.Instant

fun SearchingEntity.toTVShow(): TVShow {
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = this.releaseYear.toLocalDate(),
        genre = this.genre,
        poster = this.poster,
    )
}

fun TVShowDto.toLocal(query: String, type: String): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        time = Instant.now().epochSecond,
        id = this.id?.toLong() ?: 0L,
        title = this.name ?: "",
        rating = this.voteAverage ?: 0.0,
        releaseYear = (this.firstAirDate ?: "").toLocalDate().toString(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        mediaType = "TV",
        page = 1,
    )
}