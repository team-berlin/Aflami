package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse

interface HomeRemoteDataSource {
    suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse
    suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse
}