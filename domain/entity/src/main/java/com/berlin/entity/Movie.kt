package com.berlin.entity

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseYear: LocalDate,
    val genre: List<Int>,
    val poster: String,
    val backdropPath: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val runtime: Int? = null,
)

data class GenreEntity(val id: Int, val name: String)

data class ProductionCompanyEntity(
    val id: Int, val name: String, val poster: String? = null, val originCountry: String? = null
)