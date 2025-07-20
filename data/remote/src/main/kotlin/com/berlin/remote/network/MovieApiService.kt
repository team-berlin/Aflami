package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET(ApiConstants.MOVIE_IMAGES)
    suspend fun getMovieImages(@Path(ApiConstants.MOVIE_ID) movieId: Long): MediaImagesResponse

    @GET(ApiConstants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(ApiConstants.MOVIE_ID) id: Long,
        @Query(ApiConstants.LANGUAGE) language: String
    ): MovieDetailsDto

    @GET(ApiConstants.MOVIE_CAST)
    suspend fun getMovieCastDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long,
        @Query(ApiConstants.LANGUAGE) language: String
    ): MediaCastResponse

    @GET(ApiConstants.MOVIE_MORE_LIKE_THIS)
    suspend fun getMovieSimilar(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): MovieResponse

    @GET(ApiConstants.MOVIE_REVIEW)
    suspend fun getMovieReviews(
        @Path(ApiConstants.MOVIE_ID) id: Long
    ): ReviewResponse
}
