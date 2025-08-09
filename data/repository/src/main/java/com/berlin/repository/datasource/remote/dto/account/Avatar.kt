package com.berlin.repository.datasource.remote.dto.account


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    @SerialName("gravatar")
    val gravatar: Gravatar?,
    @SerialName("tmdb")
    val tmdb: Tmdb?
)