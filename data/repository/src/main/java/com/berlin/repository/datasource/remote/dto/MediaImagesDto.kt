package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaImageDto(
    @SerialName("file_path")
    val filePath: String?,
    @SerialName("width")
    val width: Int?,
    @SerialName("height")
    val height: Int?
)