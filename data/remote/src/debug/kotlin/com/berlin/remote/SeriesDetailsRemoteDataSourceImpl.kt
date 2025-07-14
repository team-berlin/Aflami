package com.berlin.remote

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeriesDetailsRemoteDataSourceImpl (
    private val client: HttpClient
): SeriesDetailsRemoteDataSource {
    override suspend fun getSeriesCastDetails(seriesId: Long): MediaCastResponse {
        return client.get(ApiConstants.MOVIE_CAST.replace("{series_id}", seriesId.toString())).body()

    }

}