package repository

import com.berlin.entity.Movie

interface SearchRepository {
    suspend fun getMoviesByCountry(countryName: String, language: String): List<Movie>
    suspend fun getMoviesByActorName(actorName: String, language: String): List<Movie>
}