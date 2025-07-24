package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {

    @GET(ApiConstants.POPULAR_MOVIES)
    suspend fun popularMovies(
        @Query(ApiConstants.LANGUAGE) language: String
    ): MovieResponse

    @GET(ApiConstants.POPULAR_TV_SHOWS)
    suspend fun popularTVShows(
        @Query(ApiConstants.LANGUAGE) language: String
    ): TVShowResponse
}