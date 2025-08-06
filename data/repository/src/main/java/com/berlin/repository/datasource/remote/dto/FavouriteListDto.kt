package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouriteListDto(
    @SerialName("created_by")
    val createdBy: String,

    @SerialName("description")
    val description: String,

    @SerialName("favorite_count")
    val favoriteCount: Int,

    @SerialName("id")
    val listId: Int,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("item_count")
    val itemCount: Int,

    @SerialName("items")
    val items: List<FavouriteListItem>,

    @SerialName("name")
    val listTitle: String,

    @SerialName("page")
    val page: Int,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int,
)

