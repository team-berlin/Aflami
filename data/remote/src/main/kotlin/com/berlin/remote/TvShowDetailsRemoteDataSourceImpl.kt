package com.berlin.remote

import android.util.Log
import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TvShowDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : TvShowDetailsRemoteDataSource {
    override suspend fun getSeriesImages(id: Long): MediaImagesResponse {
        Log.d("Khairy", "getMovieImages from remote data source...")
        return client.get(
            ApiConstants.SERIES_IMAGES
                .replace("id", id.toString())
        ) {
        }.body<MediaImagesResponse>()
    }

    override suspend fun getTvShowDetails(id: Long, language: String): TVShowDetailsDto {
        return client.get("tv/$id"){
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }
    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse {
        return client.get(ApiConstants.SERIES_CAST.replace("{series_id}", seriesId.toString())) {
            parameter("language", language)
        }.body()

    }
}