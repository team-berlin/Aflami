package com.berlin.repository.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouriteListDto(
    @SerialName("created_by")
    val createdBy: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("favorite_count")
    val favoriteCount: Int? = null,

    @SerialName("id")
    val listId: Int? = null,

    @SerialName("iso_639_1")
    val iso6391: String? = null,

    @SerialName("item_count")
    val itemCount: Int? = null,

    @SerialName("items")
    val favouriteListItems: List<FavouriteListItem>? = null,

    @SerialName("name")
    val listTitle: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,
)
