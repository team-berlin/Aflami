package com.berlin.entity

data class EpisodesSeason(
    val idSeason: Int? = null,
    val name: String? = null,
    val episodes: List<Episodes?>? = null,
    val seasonNumber: Int? = null,
    val posterPath: String? = null,
)
