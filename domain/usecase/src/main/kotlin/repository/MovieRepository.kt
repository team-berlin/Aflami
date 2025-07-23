package repository

import com.berlin.entity.Movie

interface MovieRepository {
    suspend fun getUpComingMovies(): List<Movie>
}