package com.berlin.entity

data class Season(
    val episodeCount: Int?,
    val id: Int?,
    val episodes: List<Episode>,
    val name: String,
    val description: String,
    val posterURL: String,
    val seasonNumber: Int,
)
