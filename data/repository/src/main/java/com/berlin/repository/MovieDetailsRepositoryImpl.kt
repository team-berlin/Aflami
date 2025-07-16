package com.berlin.repository

import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieDetailsRepository {
    override suspend fun getMovieImages(movieId: Long): List<String> {
        return try {
            movieDetailsRemoteDataSource
                .getMovieImages(movieId)
                .posters
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
        } catch (e: Exception) {
            throw e
        }
    }
}