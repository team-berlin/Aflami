package repository

import com.berlin.entity.TVShow

interface TVShowRepository {

    suspend fun getContinueWatchingTVShows(page: Int): List<TVShow>
    suspend fun addContinueWatchingTVShow(tvShow: TVShow)

    //region home movies
    suspend fun getTopRatedTVShows(page: Int): List<TVShow>
    suspend fun getPopularTVShows(): List<TVShow>
    //endregion

    //region Recent Search Queries
    suspend fun searchTVShow(query: String, page: Int): List<TVShow>
    suspend fun getRecentTVShowsSearchQueries(): List<String>
    suspend fun saveRecentTVShowsHistory(query: String)
    suspend fun deleteTVShowQueryFromHistory(query: String)
    suspend fun clearTVShowSearchHistory()
    //endregion

    //regin Game
    suspend fun getTVShowGame():List<TVShow>
    //endregion

    suspend fun getTVShowsByCategory(genreId: Long, page: Int):List<TVShow>
}