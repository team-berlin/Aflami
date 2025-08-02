package com.berlin.remote

import com.berlin.remote.network.HomeApiService
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse
import javax.inject.Inject

class HomeRemoteDataSourceImpl  @Inject constructor (
    private val homeApiService: HomeApiService
): HomeRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse {
        return homeApiService.getTopRatedMovies(page)
    }

    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return homeApiService.getTopRatedSeries(page)
    }

}