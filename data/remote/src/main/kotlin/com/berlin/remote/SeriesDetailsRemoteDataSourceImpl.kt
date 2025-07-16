package com.berlin.remote

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeriesDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : SeriesDetailsRemoteDataSource {
    override suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse {
        return client.get(ApiConstants.SERIES_MORE_LIKE_THIS
            .replace("{series_id}", seriesId.toString())).body()
    }
}