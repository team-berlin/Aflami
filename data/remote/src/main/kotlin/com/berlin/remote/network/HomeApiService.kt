package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.TopRatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET(ApiConstants.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): TopRatedResponse

    @GET(ApiConstants.TOP_RATED_SERIES)
    suspend fun getTopRatedSeries(
        @Query("page") page: Int,
    ): TopRatedResponse

}