package com.berlin.repository.mapper

import com.berlin.entity.Media
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.MediaDto
import com.berlin.repository.util.toLocalDate
import java.time.Instant

fun MediaDto.toLocal(query: String, type: QueryType, page: Int, mediaType: String?): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        time = Instant.now().epochSecond,
        id = this.id?.toLong() ?: 0L,
        title = this.title ?:this.name?:"",
        rating = this.voteAverage ?: 0.0,
        releaseYear = (releaseDate ?: ""),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        page = page,
        mediaType = mediaType?:""
    )
}

fun SearchingEntity.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = releaseYear.toLocalDate(),
        genre = this.genre,
        poster = this.poster,
        mediaType = this.mediaType
    )
}
