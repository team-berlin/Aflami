package repository

import com.berlin.entity.Genre
import com.berlin.entity.MovieDetails

import com.berlin.entity.MediaCast
import com.berlin.entity.Movie

import com.berlin.entity.Review

interface MovieDetailsRepository {
    suspend fun getMovieImages(movieId: Long): List<String>
    suspend fun getMovieDetails(id: Long): MovieDetails?
    suspend fun getMovieCastDetails(movieId: Long): List<MediaCast>
    suspend fun getMovieSimilar(movieId:Long):List<Movie>
    suspend fun getReviews(id: Long): List<Review>
    suspend fun getMovieGenres(): List<Genre>
}