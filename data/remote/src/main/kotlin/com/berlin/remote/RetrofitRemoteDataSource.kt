package com.berlin.remote

import com.berlin.remote.network.ApiService
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import javax.inject.Inject

class RetrofitRemoteDataSource @Inject constructor (
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getSimilarMovies(movieId: Long): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.getMovieSimilar(movieId) }
    }

    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        return wrapApiResponse { apiService.getMovieImages(movieId) }
    }

    override suspend fun getTVShowsImagesById(seriesId: Long): MediaImagesResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVShowsImages(seriesId) }
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

    override suspend fun getUpComingMovies(): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse {
            apiService.getUpcomingMovies()
        }
    }

    override suspend fun getTVShowDetailsById(seriesId: Long): TVShowDetailsDto {
        return wrapApiResponse { apiService.getTVShowDetails(seriesId) }
    }

    override suspend fun getTVShowsCastDetailsById(seriesId: Long): MediaCastResponse {
        return wrapApiResponse { apiService.getTVShowsCastDetails(seriesId) }
    }

    override suspend fun getSimilarTVShowsById(seriesId: Long): BaseResponse<TVShowDetailsDto> {
        return wrapApiResponse { apiService.getTVShowsSimilar(seriesId) }
    }

    override suspend fun getTVShowReviewsById(id: Long): BaseResponse<ReviewDto> {
        return wrapApiResponse { apiService.getTVShowsReviews(id) }
    }

    override suspend fun getEpisodeSeasonSeries(
        seriesId: Long, seasonNumber: Int
    ): SeasonEpisodesDto {
        return wrapApiResponse { apiService.getEpisodeSeasonSeries(seriesId, seasonNumber) }
    }

    override suspend fun getMovieGenres(): GenreResponse {
        return wrapApiResponse { apiService.getMovieGenres() }
    }

    override suspend fun getTVShowsGenres(): GenreResponse {
        return wrapApiResponse { apiService.getTVShowsGenres() }
    }

    override suspend fun getMoviesByCountryName(
        countryName: String, page: Int
    ): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse {
            apiService.searchMoviesByCountry(
                countryName, page
            )
        }
    }

    override suspend fun getMoviesByActorName(
        actorName: String, page: Int
    ): BaseResponse<PersonDto> {
        return wrapApiResponse { apiService.searchMoviesByActor(actorName, page) }
    }

    override suspend fun getMoviesByKeyword(
        query: String, page: Int
    ): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.searchMovies(query, page) }
    }

    override suspend fun getTVShowsByKeyword(
        query: String, page: Int
    ): BaseResponse<TVShowDetailsDto> {
        return wrapApiResponse { apiService.searchTVShows(query, page) }
    }

    override suspend fun getPopularMovies(): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.popularMovies() }
    }

    override suspend fun getPopularTVShows(): BaseResponse<TVShowDetailsDto> {
        return wrapApiResponse { apiService.popularTVShows() }
    }

    override suspend fun getTopRatedMovies(page: Int): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.getTopRatedMovies(page) }
    }

    override suspend fun getTopRatedTVShows(page: Int): BaseResponse<TVShowDetailsDto> {
        return wrapApiResponse { apiService.getTopRatedTVShows(page) }
    }

    override suspend fun getMoviesByMoodIds(moodIds: List<Int>): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.getMoviesByMoods(moodIds) }
    }

    override suspend fun getMovieVideos(movieId: Long): VideosResponse {
        return wrapApiResponse { apiService.getMovieVideos(movieId) }
    }

    override suspend fun getTVShowVideos(tvShowId: Long): VideosResponse {
        return wrapApiResponse { apiService.getTVShowVideos(tvShowId) }
    }

}