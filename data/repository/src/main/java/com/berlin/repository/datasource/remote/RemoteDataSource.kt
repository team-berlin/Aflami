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
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto

interface RemoteDataSource {
    suspend fun getMovieSimilar(movieId: Long): MovieResponse
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getMovieDetails(movieId: Long, language: String): MovieDetailsDto
    suspend fun getMovieCastDetails(movieId: Long, language: String): MediaCastResponse
    suspend fun getMovieReviews(movieId: Long): ReviewResponse
    suspend fun getUpComingMovies(): BaseResponse<MovieDto>

    suspend fun searchMoviesByCountry(
        countryName: String, language: String, page: Int
    ): BaseResponse<MovieDto>

    suspend fun searchMoviesByActor(
        actorName: String, language: String, page: Int
    ): BaseResponse<PersonDto>

    suspend fun searchMovies(
        query: String, language: String, page: Int
    ): BaseResponse<MovieDto>

    suspend fun searchTvShows(
        query: String, language: String, page: Int
    ): BaseResponse<TVShowDto>

    suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse
    suspend fun getTvShowDetails(seriesId: Long, language: String): TVShowDetailsDto
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse
    suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse
    suspend fun getTVReviews(id: Long): ReviewResponse
    suspend fun getEpisodeSeasonSeries(seriesId: Long, seasonNumber: Int): EpisodesSeasonDto

    suspend fun getMovieGenres(language: String): GenreResponse
    suspend fun getSeriesGenres(language: String): GenreResponse

}