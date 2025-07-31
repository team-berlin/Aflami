package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastItemDto(

    @SerialName("character")
    val character: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("id")
    val id: Int? = null,
)
