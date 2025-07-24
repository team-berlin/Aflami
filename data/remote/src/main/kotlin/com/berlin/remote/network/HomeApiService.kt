package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET(ApiConstants.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): TopRatedMoviesResponse

    @GET(ApiConstants.TOP_RATED_SERIES)
    suspend fun getTopRatedSeries(
        @Query("page") page: Int,
    ): TopRatedSeriesResponse

}