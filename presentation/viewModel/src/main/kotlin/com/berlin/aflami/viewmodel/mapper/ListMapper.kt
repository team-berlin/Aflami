package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.entity.FavouriteList

fun FavouriteList.toFavouriteListUiState() = FavouriteListItemUiState(
    listTitle = listTitle,
    numberOfFavouriteMovies = numberOfFavouriteMovies,
    listId = listId,
)