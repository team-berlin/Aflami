package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShowResponse(
    @SerialName("results")
    val results: List<TVShowDto?>? = null,
)
