package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.entity.Media
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.MediaDto
import java.time.Instant

fun MediaDto.toLocal(query: String, type: String, page: Int, mediaType: String?): SearchingEntity {
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
        releaseYear = stringToLocalDate(releaseYear),
        genre = this.genre,
        poster = this.poster,
        mediaType = this.mediaType
    )
}
fun GenreDto.toDomain(): Genre {
    return Genre(id = this.id, name = this.name)
}
