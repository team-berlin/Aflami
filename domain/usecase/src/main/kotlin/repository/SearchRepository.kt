package repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow

interface SearchRepository {
    suspend fun getMoviesByCountry(countryName: String, language: String): List<Movie>
    suspend fun getMoviesByActorName(actorName: String, language: String): List<Movie>

    suspend fun searchMovie(query: String, language: String): List<Movie>
    suspend fun searchTVShow(query: String, language: String): List<TVShow>
}