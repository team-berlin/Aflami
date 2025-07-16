package com.berlin.repository.datasource.local.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MediaImagesResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("backdrops")
    val backdrops: List<ImageDto>?,
    @SerialName("posters")
    val posters: List<ImageDto>?
)
@Serializable
data class ImageDto(
    @SerialName("file_path")
    val filePath: String?,
    @SerialName("width")
    val width: Int?,
    @SerialName("height")
    val height: Int?
)