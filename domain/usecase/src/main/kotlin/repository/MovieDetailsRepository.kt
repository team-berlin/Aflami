package repository

import com.berlin.entity.Movie

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id: Long): Movie?
}