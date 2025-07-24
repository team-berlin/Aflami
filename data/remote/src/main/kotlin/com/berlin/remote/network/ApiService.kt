package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.GenreResponse
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewResponse
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    
    @GET(ApiConstants.MOVIE_IMAGES)
    suspend fun getMovieImages(@Path(ApiConstants.MOVIE_ID) movieId: Long): Response<MediaImagesResponse>

    @GET(ApiConstants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(ApiConstants.MOVIE_ID) id: Long, @Query(ApiConstants.LANGUAGE) language: String
    ): Response<MovieDetailsDto>

    @GET(ApiConstants.MOVIE_CAST)
    suspend fun getMovieCastDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long, @Query(ApiConstants.LANGUAGE) language: String
    ): Response<MediaCastResponse>

    @GET(ApiConstants.MOVIE_MORE_LIKE_THIS)
    suspend fun getMovieSimilar(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<MovieResponse>

    @GET(ApiConstants.MOVIE_REVIEW)
    suspend fun getMovieReviews(
        @Path(ApiConstants.MOVIE_ID) id: Long
    ): Response<ReviewResponse>

    @GET(ApiConstants.SEARCH_BY_COUNTRY)
    suspend fun searchMoviesByCountry(
        @Query(ApiConstants.WITH_ORIGIN_COUNTRY) countryName: String,
        @Query(ApiConstants.LANGUAGE) language: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.SEARCH_BY_ACTOR)
    suspend fun searchMoviesByActor(
        @Query(ApiConstants.QUERY) actorName: String,
        @Query(ApiConstants.LANGUAGE) language: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<PersonDto>>

    @GET(ApiConstants.SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.LANGUAGE) language: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.SEARCH_TV)
    suspend fun searchTvShows(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.LANGUAGE) language: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<TVShowDto>>

    @GET(ApiConstants.SERIES_IMAGES)
    suspend fun getSeriesImages(@Path(ApiConstants.SERIES_ID) seriesId: Long): Response<MediaImagesResponse>

    @GET(ApiConstants.SERIES_DETAILS)
    suspend fun getTvShowDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long, @Query(ApiConstants.LANGUAGE) language: String
    ): Response<TVShowDetailsDto>

    @GET(ApiConstants.SERIES_CAST)
    suspend fun getSeriesCastDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long, @Query(ApiConstants.LANGUAGE) language: String
    ): Response<MediaCastResponse>

    @GET(ApiConstants.SERIES_MORE_LIKE_THIS)
    suspend fun getSeriesSimilar(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<TVShowResponse>

    @GET(ApiConstants.SERIES_REVIEW)
    suspend fun getSeriesReviews(
        @Path(ApiConstants.SERIES_ID) id: Long
    ): Response<ReviewResponse>

    @GET(ApiConstants.EPISODE_SEASON_SERIES)
    suspend fun getEpisodeSeasonSeries(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
        @Path(ApiConstants.SEASON_NUMBER) seasonNumber: Int
    ): Response<EpisodesSeasonDto>
    @GET(ApiConstants.MOVIE_GENRES)
    suspend fun getMovieGenres(
        @Query(ApiConstants.LANGUAGE)
        language: String): Response<GenreResponse>

    @GET(ApiConstants.TV_GENRES)
    suspend fun getTVGenres(
        @Query(ApiConstants.LANGUAGE) language: String
    ): Response<GenreResponse>

}
