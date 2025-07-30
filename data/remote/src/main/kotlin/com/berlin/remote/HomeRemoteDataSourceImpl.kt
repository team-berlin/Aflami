package com.berlin.remote

import com.berlin.remote.network.HomeApiService
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse

class HomeRemoteDataSourceImpl(
    private val homeApiService: HomeApiService
): HomeRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse {
        return homeApiService.getTopRatedMovies(page)
    }

    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return homeApiService.getTopRatedSeries(page)
    }

}