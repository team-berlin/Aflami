package com.berlin.repository.datasource.remote.response

import com.berlin.repository.datasource.remote.dto.FavouriteListDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouriteListResponse(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<FavouriteListDto>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null,
)
