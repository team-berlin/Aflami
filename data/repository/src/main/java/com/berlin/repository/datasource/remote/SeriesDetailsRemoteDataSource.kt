package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.TVShowResponse

interface SeriesDetailsRemoteDataSource {
    suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse
}