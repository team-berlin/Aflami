package com.berlin.entity

data class TvShowDetails(
    val id: Long,
    val title: String,
    val overview: String?,
    val posterUrl: String,
    val backdropUrl: String?,
    val releaseDate: String?,
    val rating: Double,
    val runtime: Int?,
    val genres: List<GenreEntity>,
    val seasons: List<SeasonEntity>,
)

data class SeasonEntity(
    val airDate: String?,
    val episodeCount: Int,
    val id: Long,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int,
    val voteAverage: Double
)

