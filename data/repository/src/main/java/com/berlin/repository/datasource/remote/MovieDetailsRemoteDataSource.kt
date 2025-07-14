package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.ReviewResponse

interface MovieDetailsRemoteDataSource {
    suspend fun getReviews(id: Long): ReviewResponse
}