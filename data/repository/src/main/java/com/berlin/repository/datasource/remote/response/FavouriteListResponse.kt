package com.berlin.repository.datasource.remote.response

import com.berlin.repository.datasource.remote.dto.FavouriteListDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouriteListResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<FavouriteListDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)
