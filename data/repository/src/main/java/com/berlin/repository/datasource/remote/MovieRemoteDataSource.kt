package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieItemDto

interface MovieRemoteDataSource {
    suspend fun getUpComingMovies(): BaseResponse<MovieItemDto>
}