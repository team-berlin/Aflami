package repository

import com.berlin.entity.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieImages(movieId: Long): List<String>
    suspend fun getMovieDetails(id: Long, language: String): MovieDetails?
}