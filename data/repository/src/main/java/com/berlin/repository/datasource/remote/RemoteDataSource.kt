package com.berlin.repository.datasource.remote

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

interface RemoteDataSource {
    suspend fun getSimilarMovies(movieId: Long): BaseResponse<MovieDetailsDto>
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getMovieDetails(movieId: Long, ): MovieDetailsDto
    suspend fun getMovieCastDetails(movieId: Long, ): MediaCastResponse
    suspend fun getMovieReviews(movieId: Long): BaseResponse<ReviewDto>
    suspend fun getUpComingMovies(): BaseResponse<MovieDetailsDto>

    suspend fun getMoviesByCountryName(
        countryName: String,  page: Int
    ): BaseResponse<MovieDetailsDto>

    suspend fun getMoviesByActorName(
        actorName: String,  page: Int
    ): BaseResponse<PersonDto>

    suspend fun getMoviesByKeyword(
        query: String,  page: Int
    ): BaseResponse<MovieDetailsDto>

    suspend fun getTVShowsByKeyword(
        query: String,  page: Int
    ): BaseResponse<TVShowDetailsDto>

    suspend fun getTVShowsImagesById(seriesId: Long): MediaImagesResponse
    suspend fun getTVShowDetailsById(seriesId: Long, ): TVShowDetailsDto
    suspend fun getTVShowsCastDetailsById(seriesId: Long, ): MediaCastResponse
    suspend fun getSimilarTVShowsById(seriesId: Long): BaseResponse<TVShowDetailsDto>
    suspend fun getTVShowReviewsById(id: Long): BaseResponse<ReviewDto>
    suspend fun getEpisodeSeasonSeries(seriesId: Long, seasonNumber: Int): SeasonEpisodesDto

    suspend fun getMovieGenres(): GenreResponse
    suspend fun getTVShowsGenres(): GenreResponse
    suspend fun getPopularMovies() : BaseResponse<MovieDetailsDto>
    suspend fun getPopularTVShows() : BaseResponse<TVShowDetailsDto>

     suspend fun getTopRatedMovies(page: Int): BaseResponse<MovieDetailsDto>

     suspend fun getTopRatedTVShows(page: Int):BaseResponse<TVShowDetailsDto>

    suspend fun getMoviesByMoodIds(
        moodIds: List<Int>
    ): BaseResponse<MovieDetailsDto>

    suspend fun getMovieVideos(movieId: Long): VideosResponse
    suspend fun getTVShowVideos(movieId: Long): VideosResponse
}