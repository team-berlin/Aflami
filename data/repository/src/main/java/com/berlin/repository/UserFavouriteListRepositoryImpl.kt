package com.berlin.repository

import com.berlin.entity.FavouriteList
import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.RemoteDataSource
import repository.UserFavouriteListRepository
import javax.inject.Inject

class UserFavouriteListRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : UserFavouriteListRepository {
    override suspend fun getUserFavouriteLists(): List<FavouriteList> {
//        remoteDataSource.getUserFavouriteLists()
        TODO()
    }

    override suspend fun getUserFavouriteListItems(
        pageNumber: Int,
        favouriteListId: Int,
    ): List<Movie> {
//        remoteDataSource.getUserFavouriteListItems(pageNumber, favouriteListId)
        TODO()
    }

    override suspend fun deleteUserFavouriteList(listId: Int) =
        remoteDataSource.deleteUserFavouriteList(listId = listId)

    override suspend fun deleteMovieFromUserFavouriteList(listId: Int, movieId: Long) =
        remoteDataSource.deleteMovieFromUserFavouriteList(listId = listId, movieId = movieId)

    override suspend fun createNewFavouriteList(title: String) =
        remoteDataSource.createNewFavouriteList(title = title)
}