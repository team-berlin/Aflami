package com.berlin.repository

import com.berlin.entity.MediaCast
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieCastDetails(movieId: Long): List<MediaCast> {
        return remoteDataSource.getMovieCastDetails(movieId).cast?.mapNotNull { castItemDto->
            castItemDto?.toDomain()
        }?: emptyList()
    }
}