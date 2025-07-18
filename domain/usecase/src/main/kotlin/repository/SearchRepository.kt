package repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow

interface SearchRepository {
    suspend fun getMoviesByCountry(query: String, page: Int): List<Movie>
    suspend fun getMoviesByActorName(actorName: String, language: String): List<Movie>

    suspend fun searchMovie(query: String, page: Int): List<Movie>
    suspend fun searchTVShow(query: String, page: Int): List<TVShow>
}