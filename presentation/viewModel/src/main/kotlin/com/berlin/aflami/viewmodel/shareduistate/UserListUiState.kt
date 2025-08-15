package com.berlin.aflami.viewmodel.shareduistate

import com.berlin.entity.FavouriteList

data class UserListUiState(
    val id: Long = 0,
    val name: String = "",
    val itemCount: Int = 0,
)

fun FavouriteList.toUiState() = UserListUiState(
    id = listId.toLong(),
    name = listTitle,
    itemCount = numberOfFavouriteMovies,
)

fun List<FavouriteList>.toUiState() = map { it.toUiState() }
