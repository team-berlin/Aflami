package repository

import com.berlin.entity.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id: Long, language: String): MovieDetails?
}