package com.berlin.remote

import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TvShowDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : TvShowDetailsRemoteDataSource {
    override suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse {
        return client.get(
            ApiConstants.SERIES_IMAGES
                .replace("id", seriesId.toString())
        ) {
        }.body<MediaImagesResponse>()
    }

    override suspend fun getTvShowDetails(seriesId: Long, language: String): TVShowDetailsDto {
        return client.get("tv/$seriesId"){
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }
    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse {
        return client.get(ApiConstants.SERIES_CAST.replace("{series_id}", seriesId.toString())) {
            parameter("language", language)
        }.body()

    }
    override suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse {
        return client.get(ApiConstants.SERIES_MORE_LIKE_THIS
            .replace(ApiConstants.SERIES_ID, seriesId.toString())).body()
    }
}