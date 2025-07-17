package com.berlin.remote

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.details.EpisodeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SeriesDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : SeriesDetailsRemoteDataSource {
    override suspend fun getEpisodeSeasonSeries(seriesId: Long, seasonNumber: Int): EpisodeResponse {
        return client.get(ApiConstants.EPISODE_SEASON_SERIES
            .replace("{series_id}", seriesId.toString())
            .replace("{episode_id}", seasonNumber.toString())).body()
    }


}