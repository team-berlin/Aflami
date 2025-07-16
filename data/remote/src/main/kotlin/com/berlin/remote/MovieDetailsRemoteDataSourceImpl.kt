package com.berlin.remote

import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.ReviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieDetailsRemoteDataSourceImpl(
    private val client: HttpClient
): MovieDetailsRemoteDataSource {
    override suspend fun getReviews(id: Long): ReviewResponse {
        return client.get("movie/$id${ApiConstants.REVIEW}").body()
    }
}