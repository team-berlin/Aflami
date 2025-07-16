package com.berlin.remote

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.ReviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeriesDetailsRemoteDataSourceImpl (
    private val client: HttpClient
): SeriesDetailsRemoteDataSource {
    override suspend fun getReviews(id: Long): ReviewResponse {
        return client.get("tv/$id${ApiConstants.REVIEW}").body()
    }
}