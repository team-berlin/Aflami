package com.berlin.remote

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SeriesDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : SeriesDetailsRemoteDataSource {
    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse {
        return client.get(ApiConstants.SERIES_CAST.replace("{series_id}", seriesId.toString())) {
            parameter("language", language)
        }.body()

    }

}