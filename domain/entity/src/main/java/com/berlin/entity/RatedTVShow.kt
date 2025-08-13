package com.berlin.entity

data class RatedTVShow(
    val id: Long,
    val name: String,
    val posterUrl: String?,
    val userRating: Double,
    val voteAverage: Double
)
