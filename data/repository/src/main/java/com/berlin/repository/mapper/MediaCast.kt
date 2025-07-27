package com.berlin.repository.mapper

import com.berlin.entity.Actor
import com.berlin.repository.datasource.remote.dto.CastItemDto

fun CastItemDto.toDomain(): Actor {
    return Actor(
        id = this.id?.toLong() ?: 0L,
        name = this.name.orEmpty(),
        poster ="${POSTER_PREFIX}${this.profilePath.orEmpty()}"
    )

}