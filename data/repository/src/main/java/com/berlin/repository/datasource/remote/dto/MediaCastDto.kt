package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MediaCastResponse(

    @SerialName("cast")
    val cast: List<CastItemDto?>? = null,

    @SerialName("id")
    val id: Int? = null

)
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
