package repository

import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.berlin.entity.Television


interface SearchRepository {
    suspend fun searchByCountry(countryName: String): List<Movie>
    suspend fun searchByActorName(actorName: String): List<MediaTypeEntity>

    suspend fun searchMovie(query: String): List<Movie>

    suspend fun searchTvShow(query: String): List<Television>
}