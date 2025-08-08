package com.berlin.repository

import com.berlin.entity.RatingResult
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.rating.SubmitRatingRequestDto
import com.berlin.repository.mapper.toDomain
import repository.RatingActionRepository
import javax.inject.Inject


class RatingActionRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RatingActionRepository {
    override suspend fun rateMovie(
        movieId: Int,
        rating: Double,
    ): RatingResult {
        val request = SubmitRatingRequestDto(value = rating)
        val response = remoteDataSource.postRateMovie(movieId, request)
        return response.toDomain()
    }

    override suspend fun rateTvShow(
        tvId: Int,
        rating: Double,
    ): RatingResult {
        val request = SubmitRatingRequestDto(value = rating)
        val response = remoteDataSource.postRateTvShow(tvId, request)
        return response.toDomain()
    }

}