package repository

import com.berlin.entity.Genre
import com.berlin.entity.Actor
import com.berlin.entity.Movie
import com.berlin.entity.Review

interface MovieDetailsRepository {
    suspend fun getMovieGallery(movieId: Long): List<String>
    suspend fun getMovieDetails(movieId: Long): Movie?
    suspend fun getMovieActors(movieId: Long): List<Actor>
    suspend fun getSimilarMovies(movieId: Long): List<Movie>
    suspend fun getMovieReviews(movieId: Long): List<Review>
    suspend fun getMovieGenres(): List<Genre>
}