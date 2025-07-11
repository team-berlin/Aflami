package repository

import com.berlin.entity.Movie

interface SearchRepository {
    suspend fun searchByCountry(countryName: String, language: String): List<Movie>
}