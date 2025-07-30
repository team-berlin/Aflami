package com.berlin.repository.datasource.remote

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
import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto

interface RemoteDataSource {
    suspend fun getMovieSimilar(movieId: Long): MovieResponse
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getMovieDetails(movieId: Long, ): MovieDetailsDto
    suspend fun getMovieCastDetails(movieId: Long, ): MediaCastResponse
    suspend fun getMovieReviews(movieId: Long): ReviewResponse
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
    suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse
    suspend fun getTVReviews(id: Long): ReviewResponse
    suspend fun getEpisodeSeasonSeries(seriesId: Long, seasonNumber: Int): EpisodesSeasonDto

    suspend fun getMovieGenres(): GenreResponse
    suspend fun getSeriesGenres(): GenreResponse
    suspend fun getPopularMovies() : MovieResponse
    suspend fun getPopularTVShows() : TVShowResponse

     suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse
     
     suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse 

    suspend fun getMoviesByMoodIds(
        moodIds: List<Int>
    ): MovieResponse

}