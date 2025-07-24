package repository

import com.berlin.entity.Media

interface HomeRepository {
    suspend fun getPopularMovies(language: String): List<Media>
    suspend fun getPopularTVShows(language: String): List<Media>
}