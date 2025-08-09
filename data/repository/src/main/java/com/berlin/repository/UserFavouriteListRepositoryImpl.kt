package com.berlin.repository

import android.util.Log
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
        Log.d("Khairy", "fav list page = $pageNumber")
        return remoteDataSource.getUserFavouriteLists(page = pageNumber)
            .map { favouriteListDto ->
                Log.d("Khairy", "repository $favouriteListDto")
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
            }.also {
                Log.d("Khairy", "getUserFavouriteList Item from repository returned $it")
            }
    }

    override suspend fun deleteUserFavouriteList(listId: Int) =
        remoteDataSource.deleteUserFavouriteList(listId = listId).also {
            Log.d("Khairy", "deleted list from remote is successful where id of deleted= $listId")
        }

    override suspend fun deleteMovieFromUserFavouriteList(listId: Int, movieId: Long) =
        remoteDataSource.deleteMovieFromUserFavouriteList(listId = listId, movieId = movieId)

    override suspend fun createNewFavouriteList(title: String) =
        remoteDataSource.createNewFavouriteList(title = title)

    override suspend fun editListTitle(listId: Int, newListTitle: String) =
        remoteDataSource.editListTitle(listId = listId, newListTitle = newListTitle)
}