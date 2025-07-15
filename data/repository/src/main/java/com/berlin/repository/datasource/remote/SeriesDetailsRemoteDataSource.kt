package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaCastResponse

interface SeriesDetailsRemoteDataSource {
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse
}