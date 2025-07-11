package com.berlin.entity

import kotlinx.datetime.LocalDate

data class MediaTypeEntity
    (
    val id: Long,
    val title: String,
    val mediaType: String,
    val rating: Double,
    val releaseYear: LocalDate,
    val genre: List<Int>,
    val poster: String
)


