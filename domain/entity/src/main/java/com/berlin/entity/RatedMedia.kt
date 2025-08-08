package com.berlin.entity

data class RatedMedia(
    val id: Long,
    val title: String,
    val posterUrl: String?,
    val userRating: Double,
    val voteAverage: Double
)
