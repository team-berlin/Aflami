package com.berlin.repository

import com.berlin.entity.Review
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getReviews(id: Long): List<Review> {
        return remoteDataSource.getReviews(id).results
            ?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() }
            ?: emptyList()
    }
}