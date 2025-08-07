package com.berlin.remote.network

import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.account.AccountDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.MOVIE_IMAGES)
    suspend fun getMovieImages(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<MediaImagesResponse>

    @GET(ApiConstants.SERIES_IMAGES)
    suspend fun getTVImages(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<MediaImagesResponse>

    @GET(ApiConstants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<MovieDetailsDto>

    @GET(ApiConstants.MOVIE_CAST)
    suspend fun getMovieCastDetails(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
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

    @GET(ApiConstants.SERIES_DETAILS)
    suspend fun getTVShowDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<TVShowDetailsDto>

    @GET(ApiConstants.SERIES_CAST)
    suspend fun getTVCastDetails(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<MediaCastResponse>

    @GET(ApiConstants.SERIES_MORE_LIKE_THIS)
    suspend fun getTVSimilar(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<BaseResponse<TVShowDetailsDto>>

    @GET(ApiConstants.SERIES_REVIEW)
    suspend fun getTVReviews(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<BaseResponse<ReviewDto>>

    @GET(ApiConstants.EPISODE_SEASON_SERIES)
    suspend fun getEpisodeSeasonSeries(
        @Path(ApiConstants.SERIES_ID) seriesId: Long,
        @Path(ApiConstants.SEASON_NUMBER) seasonNumber: Int
    ): Response<SeasonEpisodesDto>

    @GET(ApiConstants.MOVIE_GENRES)
    suspend fun getMovieGenres(): Response<GenreResponse>

    @GET(ApiConstants.SERIES_GENRES)
    suspend fun getTVGenres(): Response<GenreResponse>

    @GET(ApiConstants.DISCOVER_MOVIE)
    suspend fun getUpcomingMovies(
        @Query(ApiConstants.WITH_GENRES)  selectedGenres: Int? = null,

        @Query(ApiConstants.QUERY_SORT_BY) sortBy: String = ApiConstants.SORT_BY_POPULARITY_DESC,
        @Query(ApiConstants.QUERY_INCLUDE_ADULT) includeAdult: Boolean = ApiConstants.INCLUDE_ADULT_DEFAULT,
        @Query(ApiConstants.QUERY_INCLUDE_VIDEO) includeVideo: Boolean = ApiConstants.INCLUDE_VIDEO_DEFAULT,
        @Query(ApiConstants.QUERY_WITH_RELEASE_TYPE) releaseType: String =ApiConstants. RELEASE_TYPE_THEATRICAL_AND_LIMITED,
        @Query(ApiConstants.RELEASE_DATE_GTE)  releaseDateRangeStart: String=DEFAULT_GTE,
        @Query(ApiConstants.RELEASE_DATE_LTE)  releaseDateRangeEnd: String= DEFAULT_LTE
    )
    : Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.POPULAR_MOVIES)
    suspend fun popularMovies(): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.POPULAR_TV_SHOWS)
    suspend fun popularTVShows(): Response<BaseResponse<TVShowDetailsDto>>

    @GET(ApiConstants.DISCOVER_MOVIE)
    suspend fun getMoviesByMoods(
        @Query(ApiConstants.WITH_GENRES) genresIds: List<Int>
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<MovieDetailsDto>>

    @GET(ApiConstants.TOP_RATED_SERIES)
    suspend fun getTopRatedSeries(
        @Query(ApiConstants.PAGE) page: Int
    ): Response<BaseResponse<TVShowDetailsDto>>

    @GET(ApiConstants.TV_VIDEO_DETAILS)
    suspend fun getTVShowVideos(
        @Path(ApiConstants.SERIES_ID) seriesId: Long
    ): Response<VideosResponse>

    @GET(ApiConstants.MOVIE_VIDEO_DETAILS)
    suspend fun getMovieVideos(
        @Path(ApiConstants.MOVIE_ID) movieId: Long
    ): Response<VideosResponse>

    @GET("account")
    suspend fun getUserProfile(
        @Query(ApiConstants.SESSION_ID) sessionId: String
    ): Response<AccountDto>
}

val DEFAULT_GTE: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    .date
    .plus(1, DateTimeUnit.DAY)
    .toString()

val DEFAULT_LTE: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    .date
    .plus(21, DateTimeUnit.DAY)
    .toString()