package repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow

interface SearchRepository {
    suspend fun searchByCountry(countryName: String): List<Movie>
    suspend fun searchByActorName(actorName: String): List<Movie>

    suspend fun searchMovie(query: String): List<Movie>

    suspend fun searchTvShow(query: String): List<TVShow>
}