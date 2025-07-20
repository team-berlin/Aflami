package com.berlin.repository.mapper

import com.berlin.entity.MediaCast
import com.berlin.repository.datasource.remote.dto.CastItemDto

fun CastItemDto.toDomain(): MediaCast {
    return MediaCast(
        mediaId = this.id?.toLong() ?: 0L,
        name = this.name.orEmpty(),
        poster ="${POSTER_PREFIX}${this.profilePath.orEmpty()}"
    )

}