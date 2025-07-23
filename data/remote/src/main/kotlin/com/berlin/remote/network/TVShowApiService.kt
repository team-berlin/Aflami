package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.*
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVShowApiService {

    @GET(ApiConstants.SERIES_IMAGES)
    suspend fun getSeriesImages(@Path(ApiConstants.SERIES_ID) seriesId: Long): MediaImagesResponse

    @GET(ApiConstants.SERIES_DETAILS)
    suspend fun getTvShowDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
        @Query(ApiConstants.LANGUAGE) language: String
    ): TVShowDetailsDto

    @GET(ApiConstants.SERIES_CAST)
    suspend fun getSeriesCastDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
        @Query(ApiConstants.LANGUAGE) language: String
    ): MediaCastResponse

    @GET(ApiConstants.SERIES_MORE_LIKE_THIS)
    suspend fun getSeriesSimilar(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): TVShowResponse

    @GET(ApiConstants.SERIES_REVIEW)
    suspend fun getSeriesReviews(
        @Path(ApiConstants.SERIES_ID) id: Long
    ): ReviewResponse

    @GET(ApiConstants.EPISODE_SEASON_SERIES)
    suspend fun getEpisodeSeasonSeries(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
        @Path(ApiConstants.SEASON_NUMBER) seasonNumber: Int
    ): EpisodesSeasonDto
}
