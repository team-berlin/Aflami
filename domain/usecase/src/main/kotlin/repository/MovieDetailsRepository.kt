package repository

import com.berlin.entity.Genre

import com.berlin.entity.Actor
import com.berlin.entity.Movie

import com.berlin.entity.Review

interface MovieDetailsRepository {
    suspend fun getMovieImages(movieId: Long): List<String>
    suspend fun getMovieDetails(id: Long, language: String): MovieDetails?
    suspend fun getMovieCastDetails(movieId: Long, language: String): List<Actor>
    suspend fun getMovieSimilar(movieId:Long):List<Movie>
    suspend fun getReviews(id: Long): List<Review>
    suspend fun getMovieGenres(language: String): List<Genre>
}