package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.repository.datasource.local.dto.GenreEntity
import com.berlin.repository.datasource.remote.dto.GenreDto

fun GenreDto.toGenreEntity(type: String): GenreEntity {
    return GenreEntity(
        id = id?.toLong() ?: 0L,
        name = name ?: "",
        type = type,
        time = System.currentTimeMillis()
    )
}

fun GenreEntity.toDomain(): Genre {
    return Genre(
        id = id.toInt(),
        name = name
    )
}