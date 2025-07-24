package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.TopRatedResponse

interface HomeRemoteDataSource {
    suspend fun getTopRatedMovies(page: Int): TopRatedResponse
    suspend fun getTopRatedSeries(page: Int): TopRatedResponse
}