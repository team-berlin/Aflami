package com.berlin.repository.datasource.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveMovieFromListRequest(
    @SerialName("media_id")
    val movieId: Long,
)
