package com.berlin.repository

import com.berlin.entity.RatingResult
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.rating.SubmitRatingRequestDto
import com.berlin.repository.mapper.toDomain
import repository.RatingRepository
import javax.inject.Inject


class RatingRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RatingRepository {
    override suspend fun rateMovie(
        movieId: Int,
        rating: Double,
        sessionId: String
    ): RatingResult {
        val request = SubmitRatingRequestDto(value = rating)
        val response = remoteDataSource.postRateMovie(movieId, sessionId, request)
        return response.toDomain()
    }

    override suspend fun rateTvShow(
        tvId: Int,
        rating: Double,
        sessionId: String
    ): RatingResult {
        val request = SubmitRatingRequestDto(value = rating)
        val response = remoteDataSource.postRateTvShow(tvId, sessionId, request)
        return response.toDomain()
    }

}