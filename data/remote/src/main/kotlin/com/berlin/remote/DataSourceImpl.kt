package com.berlin.remote

import com.berlin.remote.network.ApiService
import com.berlin.repository.datasource.remote.RemoteDataSource
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

class DataSourceImpl(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getMovieSimilar(movieId: Long): MovieResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieSimilar(movieId) }
    }

    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieImages(movieId) }
    }


    override suspend fun getMovieDetails(movieId: Long, language: String): MovieDetailsDto {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        return wrapApiResponse { apiService.getMovieDetails(movieId, language) }
    }

    override suspend fun getMovieCastDetails(movieId: Long, language: String): MediaCastResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        return wrapApiResponse { apiService.getMovieCastDetails(movieId, language) }
    }

    override suspend fun getMovieReviews(movieId: Long): ReviewResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieReviews(movieId) }
    }

    override suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getSeriesImages(seriesId) }
    }

    override suspend fun getTvShowDetails(seriesId: Long, language: String): TVShowDetailsDto {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        return wrapApiResponse { apiService.getTvShowDetails(seriesId, language) }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        return wrapApiResponse { apiService.getSeriesCastDetails(seriesId, language) }
    }

    override suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getSeriesSimilar(seriesId) }
    }

    override suspend fun getTVReviews(id: Long): ReviewResponse {
        require(id > 0) { "Invalid seriesId: $id" }
        return wrapApiResponse { apiService.getSeriesReviews(id) }
    }

    override suspend fun getEpisodeSeasonSeries(
        seriesId: Long, seasonNumber: Int
    ): EpisodesSeasonDto {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        require(seasonNumber >= 0) { "Invalid seasonNumber: $seasonNumber" }
        return wrapApiResponse { apiService.getEpisodeSeasonSeries(seriesId, seasonNumber) }
    }

    override suspend fun getMovieGenres(language: String): GenreResponse {
        require(language.isNotBlank())
        return wrapApiResponse { apiService.getMovieGenres(language) }
    }

    override suspend fun getSeriesGenres(language: String): GenreResponse {
        require(language.isNotBlank())
        return wrapApiResponse { apiService.getSeriesGenres(language) }
    }

    override suspend fun searchMoviesByCountry(
        countryName: String, language: String, page: Int
    ): BaseResponse<MovieDto> {
        require(countryName.isNotBlank()) { "Country name cannot be blank" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse {
            apiService.searchMoviesByCountry(
                countryName, language, page
            )
        }
    }

    override suspend fun searchMoviesByActor(
        actorName: String, language: String, page: Int
    ): BaseResponse<PersonDto> {
        require(actorName.isNotBlank()) { "Actor name cannot be blank" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMoviesByActor(actorName, language, page) }
    }

    override suspend fun searchMovies(
        query: String, language: String, page: Int
    ): BaseResponse<MovieDto> {
        require(query.isNotBlank()) { "Query cannot be blank" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMovies(query, language, page) }
    }

    override suspend fun searchTvShows(
        query: String, language: String, page: Int
    ): BaseResponse<TVShowDto> {
        require(query.isNotBlank()) { "Query cannot be blank" }
        require(language.isNotBlank()) { "Language cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchTvShows(query, language, page) }
    }

}