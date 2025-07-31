package com.berlin.repository.datasource.remote.response

import com.berlin.repository.datasource.remote.dto.MediaImageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaImagesResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("backdrops")
    val backdrops: List<MediaImageDto>?,
    @SerialName("posters")
    val posters: List<MediaImageDto>?
)