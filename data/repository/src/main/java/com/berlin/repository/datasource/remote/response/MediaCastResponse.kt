package com.berlin.repository.datasource.remote.response

import com.berlin.repository.datasource.remote.dto.CastItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCastResponse(

    @SerialName("cast")
    val cast: List<CastItemDto>? = null,
    @SerialName("id")
    val id: Int? = null

)