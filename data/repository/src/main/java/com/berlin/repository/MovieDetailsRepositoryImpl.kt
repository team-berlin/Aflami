package com.berlin.repository

import com.berlin.entity.Genre
import com.berlin.entity.Actor
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.exception.AflamiException
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MovieDetailsRepository {
   // POSTER_PREFIX
    override suspend fun getMovieGallery(movieId: Long): List<String> {
       return try {
           remoteDataSource.getMovieImages(movieId).backdrops?.map { POSTER_PREFIX + it.filePath }
               ?: throw Exception()
       } catch (e: Exception) {
           throw e
       }
    }

    override suspend fun getMovieDetails(id: Long): Movie? {
        return try {
            remoteDataSource.getMovieDetails(id).toDomain()
        } catch (exception: AflamiException) {
            throw exception
        }
    }

    override suspend fun getMovieActors(movieId: Long): List<Actor> {
        return remoteDataSource.getMovieCastDetails(
            movieId
        ).cast?.mapNotNull { castItemDto ->
            castItemDto.toDomain()
        } ?: emptyList()
    }

    override suspend fun getSimilarMovies(movieId: Long): List<Movie> {
        return remoteDataSource.getMovieSimilar(movieId).results?.mapNotNull { movieDto ->
            movieDto.toDomain()
        } ?: emptyList()
    }

    override suspend fun getMovieReviews(movieId: Long): List<Review> {
        return remoteDataSource.getMovieReviews(movieId).results?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() } ?: emptyList()
    }



    override suspend fun getMovieGenres(): List<Genre> {
        return remoteDataSource.getMovieGenres().genres.map { it.toDomain() }
    }


}