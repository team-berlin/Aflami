package com.berlin.repository.mapper

import com.berlin.entity.Actor
import com.berlin.repository.datasource.remote.dto.CastItemDto
import com.berlin.repository.util.tmdbImageUrl

fun CastItemDto.toDomain(): Actor {
    return Actor(
        id = this.id?.toLong() ?: 0L,
        name = this.name.orEmpty(),
        posterURL = tmdbImageUrl(this.profilePath).orEmpty()
    )
}