package com.berlin.remote

import android.util.Log
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeriesDetailsRemoteDataSourceImpl(
    private val ktorClient: HttpClient,
) : SeriesDetailsRemoteDataSource {
    override suspend fun getSeriesImages(id: Long): MediaImagesResponse {
        Log.d("Khairy", "getMovieImages from remote data source...")
        return ktorClient.get(
            ApiConstants.SERIES_IMAGES
                .replace("id", id.toString())
        ) {
        }.body<MediaImagesResponse>()
    }
}