package repository

import com.berlin.entity.FavouriteList
import com.berlin.entity.Movie

interface UserFavouriteListRepository {
    suspend fun getUserFavouriteLists(): List<FavouriteList>
    suspend fun getUserFavouriteListItems(pageNumber: Int, favouriteListId: Int): List<Movie>
    suspend fun deleteUserFavouriteList(listId: Int)
    suspend fun deleteMovieFromUserFavouriteList(listId: Int, movieId: Long)
}