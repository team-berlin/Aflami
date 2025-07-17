package com.berlin.repository.datasource.remote.dto.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Season(
    @SerialName("episode_count")
    val episodeCount: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("season_number")
    val seasonNumber: Int? = null,
)