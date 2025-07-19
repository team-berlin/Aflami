package com.berlin.entity

import kotlinx.datetime.LocalDate

data class Media(
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseYear: LocalDate,
    val mediaType: String,
    val genre: List<Int>,
    val poster: String,
)
