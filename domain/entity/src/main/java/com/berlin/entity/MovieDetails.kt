package com.berlin.entity

data class MovieDetails(
    val id: Long,
    val title: String,
    val overview: String?,
    val posterUrl: String,
    val backdropUrl: String?,
    val releaseDate: String?,
    val rating: Double,
    val runtime: Int?,
    val genres: List<GenreEntity>,
    val productionCompanies: List<ProductionCompanyEntity>
)
