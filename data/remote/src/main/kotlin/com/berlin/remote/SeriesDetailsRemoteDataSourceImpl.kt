package com.berlin.remote

import com.berlin.repository.datasource.local.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeriesDetailsRemoteDataSourceImpl(
    private val ktorClient: HttpClient,
) : SeriesDetailsRemoteDataSource {
    override suspend fun getSeriesImages(id: Long): MediaImagesResponse {
        return ktorClient.get(ApiConstants.SERIES_IMAGES
            .replace("id", id.toString())) {
        }.body<MediaImagesResponse>()
    }
}