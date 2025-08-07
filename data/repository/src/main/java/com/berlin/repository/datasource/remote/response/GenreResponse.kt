package com.berlin.repository.datasource.remote.response

import com.berlin.repository.datasource.remote.dto.GenreDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("genres") val genres: List<GenreDto>
)