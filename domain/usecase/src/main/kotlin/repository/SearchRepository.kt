package repository

import com.berlin.entity.Movie

interface SearchRepository {
    suspend fun searchByCountry(countryName: String): List<Movie>
    suspend fun searchByActorName(actorName: String): List<Movie>
}