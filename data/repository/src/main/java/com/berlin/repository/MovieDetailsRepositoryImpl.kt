package com.berlin.repository

import com.berlin.entity.MovieDetails
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import exceptions.AflamiExceptions
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(id: Long, language: String): MovieDetails? {
        return try {
            remoteDataSource.getMovieDetails(id, language).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }
}