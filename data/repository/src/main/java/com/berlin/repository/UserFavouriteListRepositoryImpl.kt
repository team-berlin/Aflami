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
        remoteDataSource.getUserFavouriteLists()
    }

    override suspend fun getUserFavouriteListItems(
        pageNumber: Int,
        favouriteListId: Int,
    ): List<Movie> {
        TODO("Not yet implemented")
    }
}