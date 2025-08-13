package com.berlin.repository.mapper

import com.berlin.entity.FavouriteList
import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.dto.FavouriteListDto
import com.berlin.repository.datasource.remote.dto.FavouriteListItem
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.tmdbImageUrl

fun FavouriteListDto.toDomain(): FavouriteList {
    return FavouriteList(
        listId = listId!!, listTitle = listTitle!!, numberOfFavouriteMovies = itemCount!!
    )
}

fun FavouriteListItem.toMovie(): Movie {
    return Movie(
        id = id?.toLong()!!,
        title = title!!,
        rating = voteAverage!!,
        releaseDate = releaseDate!!,
        posterURL = tmdbImageUrl(posterPath, MediaUrls.TmdbImageSize.W500).orEmpty(),
        description = overview!!,
        genres = genreIds?.map { it.toDomainGenre() }!!,
        isFavourite = true,
        screenShot = "",
        duration = 1,
        hasVideo = false,
        companyProductions = emptyList(),
        originCountry = "TODO()",
        galleryUrl = emptyList(),
        reviews = emptyList(),
    )
}