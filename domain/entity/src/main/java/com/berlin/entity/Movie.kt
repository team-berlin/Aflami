package com.berlin.entity

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseDate: LocalDate,
    val poster: String,
    val screenShot: String,
    val description: String,
    val genres: List<Genre>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<ProductionCompany>,
    val originCountry: String,
    val galleryUrl:List<String>
)
