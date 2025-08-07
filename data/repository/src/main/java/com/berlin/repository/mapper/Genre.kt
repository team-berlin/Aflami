package com.berlin.repository.mapper

import com.berlin.entity.Genre
import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity
import com.berlin.repository.datasource.remote.dto.GenreDto


fun GenreDto.toTVShowGenreEntity(): TVShowGenreEntity {
    return TVShowGenreEntity(
        id = id?.toLong() ?: 0L,
        name = name ?: "",
        time = System.currentTimeMillis()
    )
}

fun GenreDto.toMoviesGenreEntity(): MoviesGenreEntity {
    return MoviesGenreEntity(
        id = id?.toLong() ?: 0L,
        name = name ?: "",
        time = System.currentTimeMillis()
    )
}

fun TVShowGenreEntity.toDomain(): Genre {
    return Genre(
        id = id.toInt(),
        name = name
    )
}

fun MoviesGenreEntity.toDomain(): Genre {
    return Genre(
        id = id.toInt(),
        name = name
    )
}