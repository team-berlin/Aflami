package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET(ApiConstants.SEARCH_BY_COUNTRY)
    suspend fun searchMoviesByCountry(
        @Query(ApiConstants.WITH_ORIGIN_COUNTRY) countryName: String,
        @Query(ApiConstants.LANGUAGE) language: String,
        @Query(ApiConstants.PAGE) page: Int
    ): BaseResponse<MovieDto>

    @GET(ApiConstants.SEARCH_BY_ACTOR)
    suspend fun searchMoviesByActor(
        @Query(ApiConstants.QUERY) actorName: String,
        @Query(ApiConstants.LANGUAGE) language: String,
        @Query(ApiConstants.PAGE) page: Int
    ): BaseResponse<PersonDto>

    @GET(ApiConstants.SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.LANGUAGE) language: String
    ): MovieResponse

    @GET(ApiConstants.SEARCH_TV)
    suspend fun searchTvShows(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.LANGUAGE) language: String
    ): TVShowResponse
}
