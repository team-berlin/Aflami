package com.berlin.repository

import com.berlin.entity.MediaCast
import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.entity.MovieDetails
import com.berlin.entity.Review
import exceptions.AflamiExceptions
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieCastDetails(movieId: Long, language: String): List<MediaCast> {
        return remoteDataSource.getMovieCastDetails(
            movieId,
            language
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        }?: emptyList()
    }

    override suspend fun getMovieImages(movieId: Long): List<String> {
        return try {
            remoteDataSource
                .getMovieImages(movieId)
                .backdrops
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieDetails(id: Long, language: String): MovieDetails? {
        return try {
            remoteDataSource.getMovieDetails(id, language).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }
    override suspend fun getMovieSimilar(movieId: Long): List<Movie> {
        return remoteDataSource.getMovieSimilar(movieId).results?.mapNotNull { movieDto ->
            movieDto?.toDomain()
        } ?: emptyList()
    }

    override suspend fun getReviews(id: Long): List<Review> {
        return remoteDataSource.getReviews(id).results
            ?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() }
            ?: emptyList()
    }
}