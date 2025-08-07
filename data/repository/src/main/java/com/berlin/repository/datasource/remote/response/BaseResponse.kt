package com.berlin.repository.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(

    @SerialName("id")
    val id: Int? = null,

    @SerialName("page")
    val page: Int? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("results")
    val results: List<T>? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)