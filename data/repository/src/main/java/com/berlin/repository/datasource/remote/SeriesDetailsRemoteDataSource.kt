package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.details.EpisodeResponse

interface SeriesDetailsRemoteDataSource {
    suspend fun getEpisodeSeasonSeries(seriesId: Long, seasonNumber: Int): EpisodeResponse
}