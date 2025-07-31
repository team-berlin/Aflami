package repository

import com.berlin.entity.Media
import com.berlin.entity.Movie

interface MovieRepository {
    suspend fun getUpComingMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Media>
    suspend fun getPopularTVShows(): List<Media>
    suspend fun getMoviesByMoods(moods: List<Int>): List<Movie>
}