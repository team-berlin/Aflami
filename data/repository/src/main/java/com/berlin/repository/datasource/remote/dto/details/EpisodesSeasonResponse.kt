package com.berlin.repository.datasource.remote.dto.details

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class EpisodesSeasonResponse(
    @SerialName("results")
    val results: List<SeasonEpisodesDto?>? = null,
)
