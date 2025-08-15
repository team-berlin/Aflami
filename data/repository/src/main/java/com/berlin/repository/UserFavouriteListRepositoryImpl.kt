package com.berlin.repository

import com.berlin.entity.FavouriteList
import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toMovie
import repository.UserFavouriteListRepository
import javax.inject.Inject

class UserFavouriteListRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : UserFavouriteListRepository {
    override suspend fun getUserFavouriteLists(pageNumber: Int): List<FavouriteList> {
        return remoteDataSource.getUserFavouriteLists(page = pageNumber)
            .ifEmpty { return emptyList() }
            .map { favouriteListDto ->
                favouriteListDto.toDomain()
            }
    }

    override suspend fun getUserFavouriteListItems(
        pageNumber: Int,
        favouriteListId: Int,
    ): List<Movie> {
        return remoteDataSource.getUserFavouriteListItems(pageNumber, favouriteListId)
            .map { favouriteListItem ->
                favouriteListItem.toMovie()
            }
    }

    override suspend fun deleteUserFavouriteList(listId: Int) =
        remoteDataSource.deleteUserFavouriteList(listId = listId)

    override suspend fun deleteMovieFromUserFavouriteList(listId: Int, movieId: Long) =
        remoteDataSource.deleteMovieFromUserFavouriteList(listId = listId, movieId = movieId)

    override suspend fun createNewFavouriteList(title: String): Int =
        remoteDataSource.createNewFavouriteList(title = title)

    override suspend fun editListTitle(listId: Int, newListTitle: String) =
        remoteDataSource.editListTitle(listId = listId, newListTitle = newListTitle)

    override suspend fun addMovieToFavouriteList(movieId: Long, listId: Int) =
        remoteDataSource.addMovieToFavouriteList(movieId = movieId, listId = listId)
}