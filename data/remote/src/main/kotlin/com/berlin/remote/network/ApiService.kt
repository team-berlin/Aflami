package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstants.MOVIE_IMAGES)
    suspend fun getMovieImages(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<MediaImagesResponse>

    @GET(ApiConstants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(ApiConstants.MOVIE_ID) id: Long,
    ): Response<MovieDetailsDto>

    @GET(ApiConstants.MOVIE_CAST)
    suspend fun getMovieCastDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long,
    ): Response<MediaCastResponse>

    @GET(ApiConstants.MOVIE_MORE_LIKE_THIS)
    suspend fun getMovieSimilar(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.MOVIE_REVIEW)
    suspend fun getMovieReviews(
        @Path(ApiConstants.MOVIE_ID) id: Long
    ): Response<BaseResponse<ReviewDto>>

    @GET(ApiConstants.SEARCH_BY_COUNTRY)
    suspend fun searchMoviesByCountry(
        @Query(ApiConstants.WITH_ORIGIN_COUNTRY) countryName: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.SEARCH_BY_ACTOR)
    suspend fun searchMoviesByActor(
        @Query(ApiConstants.QUERY) actorName: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<PersonDto>>

    @GET(ApiConstants.SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.SEARCH_TV)
    suspend fun searchTvShows(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<TVShowDto>>

    @GET(ApiConstants.SERIES_IMAGES)
    suspend fun getSeriesImages(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<MediaImagesResponse>

    @GET(ApiConstants.SERIES_DETAILS)
    suspend fun getTvShowDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
    ): Response<TVShowDetailsDto>

    @GET(ApiConstants.SERIES_CAST)
    suspend fun getSeriesCastDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
    ): Response<MediaCastResponse>

    @GET(ApiConstants.SERIES_MORE_LIKE_THIS)
    suspend fun getSeriesSimilar(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<BaseResponse<TVShowDto>>

    @GET(ApiConstants.SERIES_REVIEW)
    suspend fun getSeriesReviews(
        @Path(ApiConstants.SERIES_ID) id: Long
    ): Response<BaseResponse<ReviewDto>>

    @GET(ApiConstants.EPISODE_SEASON_SERIES)
    suspend fun getEpisodeSeasonSeries(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
        @Path(ApiConstants.SEASON_NUMBER) seasonNumber: Int
    ): Response<EpisodesSeasonDto>

    @GET(ApiConstants.MOVIE_GENRES)
    suspend fun getMovieGenres(
    ): Response<GenreResponse>

    @GET(ApiConstants.SERIES_GENRES)
    suspend fun getSeriesGenres(
    ): Response<GenreResponse>

    @GET(ApiConstants.MOVIE_UPCOMING)
    suspend fun getUpcomingMovies():
            Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.POPULAR_MOVIES)
    suspend fun popularMovies(
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.POPULAR_TV_SHOWS)
    suspend fun popularTVShows(
    ): Response<BaseResponse<TVShowDto>>

    @GET(ApiConstants.DISCOVER_MOVIE)
    suspend fun getMoviesByMoods(
        @Query(ApiConstants.WITH_GENRES) genresIds: List<Int>,
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): Response<BaseResponse<MovieDto>>

    @GET(ApiConstants.TOP_RATED_SERIES)
    suspend fun getTopRatedSeries(
        @Query("page") page: Int,
    ): Response<BaseResponse<TVShowDto>>

}
