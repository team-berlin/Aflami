package com.berlin.repository.mapper

import com.berlin.entity.FavouriteList
import com.berlin.repository.datasource.remote.dto.FavouriteListDto

fun FavouriteListDto.toDomain(): FavouriteList {
    return FavouriteList(
        listId = listId,
        listTitle = listTitle,
        numberOfFavouriteMovies = favoriteCount
    )
}

