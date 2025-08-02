package com.berlin.remote

import com.berlin.remote.network.ApiService
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDto
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import javax.inject.Inject

class RetrofitRemoteDataSource @Inject constructor (
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getMovieSimilar(movieId: Long): BaseResponse<MovieDto> {
        return wrapApiResponse { apiService.getMovieSimilar(movieId) }
    }

    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        return wrapApiResponse { apiService.getMovieImages(movieId) }
    }

    override suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getSeriesImages(seriesId) }
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetailsDto {
        return wrapApiResponse { apiService.getMovieDetails(movieId) }
    }

    override suspend fun getMovieCastDetails(movieId: Long): MediaCastResponse {
        return wrapApiResponse { apiService.getMovieCastDetails(movieId) }
    }

    override suspend fun getMovieReviews(movieId: Long): BaseResponse<ReviewDto> {
        return wrapApiResponse { apiService.getMovieReviews(movieId) }
    }

    override suspend fun getUpComingMovies(): BaseResponse<MovieDto> {
        return wrapApiResponse {
            apiService.getUpcomingMovies()
        }
    }

    override suspend fun getTvShowDetails(seriesId: Long): TVShowDetailsDto {
        return wrapApiResponse { apiService.getTvShowDetails(seriesId) }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long): MediaCastResponse {
        return wrapApiResponse { apiService.getSeriesCastDetails(seriesId) }
    }

    override suspend fun getSeriesSimilar(seriesId: Long): BaseResponse<TVShowDto> {
        return wrapApiResponse { apiService.getSeriesSimilar(seriesId) }
    }

    override suspend fun getTVReviews(id: Long): BaseResponse<ReviewDto> {
        return wrapApiResponse { apiService.getSeriesReviews(id) }
    }

    override suspend fun getEpisodeSeasonSeries(
        seriesId: Long, seasonNumber: Int
    ): EpisodesSeasonDto {
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
        return wrapApiResponse {
            apiService.searchMoviesByCountry(
                countryName, page
            )
        }
    }

    override suspend fun searchMoviesByActor(
        actorName: String, page: Int
    ): BaseResponse<PersonDto> {
        return wrapApiResponse { apiService.searchMoviesByActor(actorName, page) }
    }

    override suspend fun searchMovies(
        query: String, page: Int
    ): BaseResponse<MovieDto> {
        return wrapApiResponse { apiService.searchMovies(query, page) }
    }

    override suspend fun searchTvShows(
        query: String, page: Int
    ): BaseResponse<TVShowDto> {
        return wrapApiResponse { apiService.searchTvShows(query, page) }
    }

    override suspend fun getPopularMovies(): BaseResponse<MovieDto> {
        return wrapApiResponse { apiService.popularMovies() }
    }

    override suspend fun getPopularTVShows(): BaseResponse<TVShowDto> {
        return wrapApiResponse { apiService.popularTVShows() }
    }

    override suspend fun getTopRatedMovies(page: Int): BaseResponse<MovieDto> {
        return wrapApiResponse { apiService.getTopRatedMovies(page) }
    }

    override suspend fun getTopRatedSeries(page: Int): BaseResponse<TVShowDto> {
        return wrapApiResponse { apiService.getTopRatedSeries(page) }
    }

    override suspend fun getMoviesByMoodIds(moodIds: List<Int>): BaseResponse<MovieDto> {
        return wrapApiResponse { apiService.getMoviesByMoods(moodIds) }
    }

    override suspend fun getMovieVideos(movieId: Long): VideosResponse {
        return wrapApiResponse { apiService.getMovieVideos(movieId) }
    }

    override suspend fun getTVShowVideos(tvShowId: Long): VideosResponse {
        return wrapApiResponse { apiService.getTvShowVideos(tvShowId) }
    }

}