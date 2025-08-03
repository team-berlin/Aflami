package repository

import com.berlin.entity.ContinueWatchingModel
import com.berlin.entity.TVShow

interface TVShowRepository {
    suspend fun getContinueWatchingTVShows(page: Int): List<TVShow>
    suspend fun addContinueWatchingTVShow(continueWatchingModel: ContinueWatchingModel)
    suspend fun getTopRatedSeries(page: Int): List<TVShow>
    suspend fun getPopularTVShows(): List<TVShow>
    suspend fun searchTVShow(query: String, page: Int): List<TVShow>
    suspend fun getRecentTVShowsSearchQueries(): List<String>
    suspend fun saveRecentTVShowsHistory(query: String)
    suspend fun deleteTVShowQueryFromHistory(query: String)
    suspend fun clearTVShowSearchHistory()
}