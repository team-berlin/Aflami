package com.berlin.repository

import com.berlin.entity.Review
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.SeriesDetailsRepository

class SeriesDetailsRepositoryImpl(
    private val remoteDataSource: SeriesDetailsRemoteDataSource
) : SeriesDetailsRepository {
    override suspend fun getReviews(id: Long): List<Review> {
        return remoteDataSource.getReviews(id).results
            ?.filterNotNull()
            ?.map {  reviewDto -> reviewDto.toDomain() }
            ?: emptyList()
    }

}