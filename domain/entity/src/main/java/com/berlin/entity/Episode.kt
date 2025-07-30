package com.berlin.entity

data class Episode(
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val episodeId: Long,
    val name: String,
    val description: String,
    val duration: String,
    val tvShowId: Int,
    val rating: Double,
)
