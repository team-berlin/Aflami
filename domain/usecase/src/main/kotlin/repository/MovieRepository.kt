package repository

import com.berlin.entity.Movie

interface MovieRepository {
    suspend fun getUpComingMovies(): List<Movie>
    suspend fun getPopularMovies(language: String): List<Media>
    suspend fun getPopularTVShows(language: String): List<Media>
}