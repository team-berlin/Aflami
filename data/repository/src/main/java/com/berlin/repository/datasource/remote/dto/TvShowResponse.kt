package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowResponse(

    val page: Int? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    val results: List<TvShowDto?>? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)
