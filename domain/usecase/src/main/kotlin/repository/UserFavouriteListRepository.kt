package repository

import com.berlin.entity.FavouriteList
import com.berlin.entity.Movie

interface UserFavouriteListRepository {
    suspend fun getUserFavouriteLists(pageNumber: Int): List<FavouriteList>
    suspend fun getUserFavouriteListItems(pageNumber: Int, favouriteListId: Int): List<Movie>
    suspend fun deleteUserFavouriteList(listId: Int)
    suspend fun deleteMovieFromUserFavouriteList(listId: Int, movieId: Long)
    suspend fun createNewFavouriteList(title: String)
    suspend fun editListTitle(listId: Int, newListTitle: String)
    suspend fun addMovieToFavouriteList(movieId: Long, listId: Int)
}