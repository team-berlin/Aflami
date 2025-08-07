package com.berlin.repository.mapper

import com.berlin.entity.FavouriteList
import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.dto.FavouriteListDto
import com.berlin.repository.datasource.remote.dto.FavouriteListItem

fun FavouriteListDto.toDomain(): FavouriteList {
    return FavouriteList(
        listId = listId,
        listTitle = listTitle,
        numberOfFavouriteMovies = favoriteCount
    )
}

fun FavouriteListItem.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        rating = voteAverage,
        releaseDate = releaseDate,
        posterURL = posterPath ?: "No Poster",
        description = overview,
        genres = genreIds.map { it.toDomainGenre() },
        isFavourite = true,
    )
}

