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
import com.berlin.repository.datasource.remote.dto.TopRatedMoviesResponse
import com.berlin.repository.datasource.remote.dto.TopRatedSeriesResponse
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto

class RetrofitRemoteDataSource(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getMovieSimilar(movieId: Long): BaseResponse<MovieResponse> {
        return wrapApiResponse { apiService.getMovieSimilar(movieId) }
    }

    override suspend fun getMovieImages(movieId: Long): BaseResponse<MediaImagesResponse> {
        return wrapApiResponse { apiService.getMovieImages(movieId) }
    }

    override suspend fun getMovieDetails(movieId: Long): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.getMovieDetails(movieId) }
    }

    override suspend fun getMovieCastDetails(movieId: Long): BaseResponse<MediaCastResponse> {
        return wrapApiResponse { apiService.getMovieCastDetails(movieId) }
    }

    override suspend fun getMovieReviews(movieId: Long): BaseResponse<ReviewResponse> {
        return wrapApiResponse { apiService.getMovieReviews(movieId) }
    }

    override suspend fun getUpComingMovies(): BaseResponse<MovieDto> {
        return wrapApiResponse {
            apiService.getUpcomingMovies()
        }
    }

    override suspend fun getSeriesImages(seriesId: Long): BaseResponse<MediaImagesResponse> {
        return wrapApiResponse { apiService.getSeriesImages(seriesId) }
    }

    override suspend fun getTvShowDetails(seriesId: Long): BaseResponse<TVShowDetailsDto> {
        return wrapApiResponse { apiService.getTvShowDetails(seriesId) }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long): BaseResponse<MediaCastResponse> {
        return wrapApiResponse { apiService.getSeriesCastDetails(seriesId) }
    }

    override suspend fun getSeriesSimilar(seriesId: Long): BaseResponse<TVShowResponse> {
        return wrapApiResponse { apiService.getSeriesSimilar(seriesId) }
    }

    override suspend fun getTVReviews(id: Long): BaseResponse<ReviewResponse> {
        return wrapApiResponse { apiService.getSeriesReviews(id) }
    }

    override suspend fun getEpisodeSeasonSeries(
        seriesId: Long, seasonNumber: Int
    ): BaseResponse<EpisodesSeasonDto> {
        return wrapApiResponse { apiService.getEpisodeSeasonSeries(seriesId, seasonNumber) }
    }

    override suspend fun getMovieGenres(): BaseResponse<GenreResponse> {
        return wrapApiResponse { apiService.getMovieGenres() }
    }

    override suspend fun getSeriesGenres(): BaseResponse<GenreResponse> {
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

    override suspend fun getPopularMovies(): BaseResponse<MovieResponse> {
        return wrapApiResponse { apiService.popularMovies() }
    }

    override suspend fun getPopularTVShows(): BaseResponse<TVShowResponse> {
        return wrapApiResponse { apiService.popularTVShows() }
    }

    override suspend fun getTopRatedMovies(page: Int): BaseResponse<TopRatedMoviesResponse> {
        return wrapApiResponse { apiService.getTopRatedMovies(page) }
    }

    override suspend fun getTopRatedSeries(page: Int): BaseResponse<TopRatedSeriesResponse> {
        return wrapApiResponse { apiService.getTopRatedSeries(page) }
    }

    override suspend fun getMoviesByMoodIds(moodIds: List<Int>): BaseResponse<MovieResponse> {
        return wrapApiResponse { apiService.getMoviesByMoods(moodIds) }
    }

}