package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse

interface RemoteDataSource {
    suspend fun getMovieSimilar(movieId: Long): BaseResponse<MovieDto>
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getMovieDetails(movieId: Long, ): MovieDetailsDto
    suspend fun getMovieCastDetails(movieId: Long, ): MediaCastResponse
    suspend fun getMovieReviews(movieId: Long): BaseResponse<ReviewDto>
    suspend fun getUpComingMovies(): BaseResponse<MovieDto>

    suspend fun searchMoviesByCountry(
        countryName: String,  page: Int
    ): BaseResponse<MovieDto>

    suspend fun searchMoviesByActor(
        actorName: String,  page: Int
    ): BaseResponse<PersonDto>

    suspend fun searchMovies(
        query: String,  page: Int
    ): BaseResponse<MovieDto>

    suspend fun searchTvShows(
        query: String,  page: Int
    ): BaseResponse<TVShowDto>

    suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse
    suspend fun getTvShowDetails(seriesId: Long, ): TVShowDetailsDto
    suspend fun getSeriesCastDetails(seriesId: Long, ): MediaCastResponse
    suspend fun getSeriesSimilar(seriesId: Long): BaseResponse<TVShowDto>
    suspend fun getTVReviews(id: Long): BaseResponse<ReviewDto>
    suspend fun getEpisodeSeasonSeries(seriesId: Long, seasonNumber: Int): EpisodesSeasonDto

    suspend fun getMovieGenres(): GenreResponse
    suspend fun getSeriesGenres(): GenreResponse
    suspend fun getPopularMovies() : BaseResponse<MovieDto>
    suspend fun getPopularTVShows() : BaseResponse<TVShowDto>

     suspend fun getTopRatedMovies(page: Int): BaseResponse<MovieDto>

     suspend fun getTopRatedSeries(page: Int):BaseResponse<TVShowDto>

    suspend fun getMoviesByMoodIds(
        moodIds: List<Int>
    ): BaseResponse<MovieDto>

    suspend fun getMovieVideos(movieId: Long): VideosResponse
    suspend fun getTVShowVideos(movieId: Long): VideosResponse



}