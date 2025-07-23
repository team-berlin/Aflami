package com.berlin.remote

import com.berlin.remote.network.MovieApiService
import com.berlin.repository.datasource.remote.MovieRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieItemDto

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApiService
) : MovieRemoteDataSource {
    override suspend fun getUpComingMovies(): BaseResponse<MovieItemDto> {
        return movieApi.getUpcomingMovie()
    }
}