package com.berlin.repository

import com.berlin.entity.MovieDetails
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import exceptions.AflamiExceptions
import com.berlin.repository.mapper.POSTER_PREFIX
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {

    override suspend fun getMovieImages(movieId: Long): List<String> {
        return try {
            remoteDataSource
                .getMovieImages(movieId)
                .posters
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
}