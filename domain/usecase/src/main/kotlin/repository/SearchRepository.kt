package repository

import com.berlin.entity.Movie

interface SearchRepository {
    suspend fun searchByCountry(countryName: String, language: String): List<Movie>
    suspend fun searchByActor(actorName: String, language: String): List<Movie>
}