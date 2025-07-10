package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaByActorResponse(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("results")
    val results: PersonDto?? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)
