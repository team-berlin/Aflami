package com.berlin.remote.network

import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
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

    @GET(ApiConstants.TV_SHOW_IMAGES)
    suspend fun getTVShowsImages(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long
    ): Response<MediaImagesResponse>

    @GET(ApiConstants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long,
    ): Response<MovieDetailsDto>

    @GET(ApiConstants.MOVIE_CAST)
    suspend fun getMovieCastDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long,
    ): Response<MediaCastResponse>

    @GET(ApiConstants.MOVIE_MORE_LIKE_THIS)
    suspend fun getMovieSimilar(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.MOVIE_REVIEW)
    suspend fun getMovieReviews(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<BaseResponse<ReviewDto>>

    @GET(ApiConstants.SEARCH_BY_COUNTRY)
    suspend fun searchMoviesByCountry(
        @Query(ApiConstants.WITH_ORIGIN_COUNTRY) countryName: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.SEARCH_BY_ACTOR)
    suspend fun searchMoviesByActor(
        @Query(ApiConstants.QUERY) actorName: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<PersonDto>>

    @GET(ApiConstants.SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.SEARCH_TV)
    suspend fun searchTVShows(
        @Query(ApiConstants.QUERY) query: String,
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<TVShowDetailsDto>>


    @GET(ApiConstants.TV_SHOW_DETAILS)
    suspend fun getTVShowDetails(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long,
    ): Response<TVShowDetailsDto>

    @GET(ApiConstants.TV_SHOW_CAST)
    suspend fun getTVShowsCastDetails(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long,
    ): Response<MediaCastResponse>

    @GET(ApiConstants.TV_SHOW_MORE_LIKE_THIS)
    suspend fun getTVShowsSimilar(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long
    ): Response<BaseResponse<TVShowDetailsDto>>

    @GET(ApiConstants.TV_SHOW_REVIEW)
    suspend fun getTVShowsReviews(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long
    ): Response<BaseResponse<ReviewDto>>

    @GET(ApiConstants.EPISODE_SEASON_SERIES)
    suspend fun getEpisodeSeasonSeries(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long,
        @Path(ApiConstants.SEASON_NUMBER) seasonNumber: Int
    ): Response<SeasonEpisodesDto>

    @GET(ApiConstants.MOVIE_GENRES)
    suspend fun getMovieGenres(
    ): Response<GenreResponse>

    @GET(ApiConstants.TV_SHOW_GENRES)
    suspend fun getTVShowsGenres(
    ): Response<GenreResponse>

    @GET(ApiConstants.MOVIE_UPCOMING)
    suspend fun getUpcomingMovies():
            Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.POPULAR_MOVIES)
    suspend fun popularMovies(
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.POPULAR_TV_SHOWS)
    suspend fun popularTVShows(
    ): Response<BaseResponse<TVShowDetailsDto>>

    @GET(ApiConstants.DISCOVER_MOVIE)
    suspend fun getMoviesByMoods(
        @Query(ApiConstants.WITH_GENRES) genresIds: List<Int>,
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(
        @Query(ApiConstants.PAGE) page: Int,
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.TOP_RATED_SERIES)
    suspend fun getTopRatedTVShows(
        @Query(ApiConstants.PAGE) page: Int,
    ): Response<BaseResponse<TVShowDetailsDto>>

    @GET(ApiConstants.TV_VIDEO_DETAILS)
    suspend fun getTVShowVideos(
        @Path(ApiConstants.TV_SHOW_ID) tvShowId: Long
    ): Response<VideosResponse>

    @GET(ApiConstants.MOVIE_VIDEO_DETAILS)
    suspend fun getMovieVideos(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<VideosResponse>
}
