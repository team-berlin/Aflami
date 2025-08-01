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
import com.berlin.repository.datasource.remote.dto.details.VideosResponse

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


    override suspend fun getMovieDetails(movieId: Long): MovieDetailsDto {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieDetails(movieId) }
    }

    override suspend fun getMovieCastDetails(movieId: Long): MediaCastResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieCastDetails(movieId) }
    }

    override suspend fun getMovieReviews(movieId: Long): ReviewResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieReviews(movieId) }
    }

    override suspend fun getUpComingMovies(): BaseResponse<MovieDto> {
        return wrapApiResponse {
            apiService.getUpcomingMovies()
        }
    }

    override suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getSeriesImages(seriesId) }
    }

    override suspend fun getTvShowDetails(seriesId: Long): TVShowDetailsDto {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTvShowDetails(seriesId) }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long): MediaCastResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getSeriesCastDetails(seriesId) }
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

    override suspend fun getMovieGenres(): GenreResponse {
        return wrapApiResponse { apiService.getMovieGenres() }
    }

    override suspend fun getSeriesGenres(): GenreResponse {
        return wrapApiResponse { apiService.getSeriesGenres() }
    }

    override suspend fun searchMoviesByCountry(
        countryName: String, page: Int
    ): BaseResponse<MovieDto> {
        require(countryName.isNotBlank()) { "Country name cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse {
            apiService.searchMoviesByCountry(
                countryName, page
            )
        }
    }

    override suspend fun searchMoviesByActor(
        actorName: String, page: Int
    ): BaseResponse<PersonDto> {
        require(actorName.isNotBlank()) { "Actor name cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMoviesByActor(actorName, page) }
    }

    override suspend fun searchMovies(
        query: String, page: Int
    ): BaseResponse<MovieDto> {
        require(query.isNotBlank()) { "Query cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMovies(query, page) }
    }

    override suspend fun searchTvShows(
        query: String, page: Int
    ): BaseResponse<TVShowDto> {
        require(query.isNotBlank()) { "Query cannot be blank" }
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchTvShows(query, page) }
    }

    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.popularMovies()
    }

    override suspend fun getPopularTVShows(): TVShowResponse {
        return apiService.popularTVShows()
    }

    override suspend fun getMoviesByMoodIds(moodIds: List<Int>): MovieResponse {
        require(moodIds.isNotEmpty()) { "Mood IDs list cannot be empty" }
        return wrapApiResponse { apiService.getMoviesByMoods(moodIds) }
    }

    override suspend fun getMovieVideos(movieId: Long): VideosResponse {
        return wrapApiResponse { apiService.getMovieVideos(movieId) }
    }

    override suspend fun getTVShowVideos(tvShowId: Long): VideosResponse {
        return wrapApiResponse { apiService.getTvShowVideos(tvShowId) }
    }

}