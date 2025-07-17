package repository

import com.berlin.entity.MovieDetails

import com.berlin.entity.MediaCast

interface MovieDetailsRepository {
    suspend fun getMovieImages(movieId: Long): List<String>
    suspend fun getMovieDetails(id: Long, language: String): MovieDetails?
    suspend fun getMovieCastDetails(movieId: Long, language: String): List<MediaCast>
}