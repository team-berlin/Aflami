package com.berlin.remote

import com.berlin.remote.network.HomeApiService
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.dto.TopRatedResponse

class HomeRemoteDataSourceImpl(
    private val homeApiService: HomeApiService
): HomeRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): TopRatedResponse {
        return homeApiService.getTopRatedMovies(page)
    }

    override suspend fun getTopRatedSeries(page: Int): TopRatedResponse {
        return homeApiService.getTopRatedSeries(page)
    }

}