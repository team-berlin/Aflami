package repository

import com.berlin.entity.Media
import com.berlin.entity.Movie
import com.berlin.entity.TVShow

interface SearchRepository {
    suspend fun getMoviesByCountry(query: String, page: Int): List<Movie>
    suspend fun getMediaByActorName(actorName: String, page: Int): List<Media>

    suspend fun searchMovie(query: String, page: Int): List<Movie>
    suspend fun searchTVShow(query: String, page: Int): List<TVShow>

    suspend fun getRecentSearchQueries(): List<String>
    suspend fun saveRecentHistory(query: String)
    suspend fun deleteQueryFromHistory(query: String)
    suspend fun clearSearchHistory()
}