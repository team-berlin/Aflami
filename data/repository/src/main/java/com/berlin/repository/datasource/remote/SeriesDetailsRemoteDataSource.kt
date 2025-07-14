package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.ReviewResponse

interface SeriesDetailsRemoteDataSource {
    suspend fun getReviews(id: Long): ReviewResponse

}