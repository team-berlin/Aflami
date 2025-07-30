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

    override suspend fun getMovieSimilar(movieId: Long): MovieResponse {
        return wrapApiResponse { apiService.getMovieSimilar(movieId) }
    }

    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        return wrapApiResponse { apiService.getMovieImages(movieId) }
    }


    override suspend fun getMovieDetails(movieId: Long, ): MovieDetailsDto {
        return wrapApiResponse { apiService.getMovieDetails(movieId, ) }
    }

    override suspend fun getMovieCastDetails(movieId: Long, ): MediaCastResponse {
        return wrapApiResponse { apiService.getMovieCastDetails(movieId, ) }
    }

    override suspend fun getMovieReviews(movieId: Long): ReviewResponse {
        return wrapApiResponse { apiService.getMovieReviews(movieId) }
    }

    override suspend fun getUpComingMovies(): BaseResponse<MovieDto> {
        return wrapApiResponse {
            apiService.getUpcomingMovies()
        }
    }

    override suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse {
        return wrapApiResponse { apiService.getSeriesImages(seriesId) }
    }

    override suspend fun getTvShowDetails(seriesId: Long, ): TVShowDetailsDto {
        return wrapApiResponse { apiService.getTvShowDetails(seriesId) }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long, ): MediaCastResponse {
        return wrapApiResponse { apiService.getSeriesCastDetails(seriesId, ) }
    }

    override suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse {
        return wrapApiResponse { apiService.getSeriesSimilar(seriesId) }
    }

    override suspend fun getTVReviews(id: Long): ReviewResponse {
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
        countryName: String,  page: Int
    ): BaseResponse<MovieDto> {
        return wrapApiResponse {
            apiService.searchMoviesByCountry(
                countryName, page
            )
        }
    }

    override suspend fun searchMoviesByActor(
        actorName: String,  page: Int
    ): BaseResponse<PersonDto> {
        return wrapApiResponse { apiService.searchMoviesByActor(actorName, page) }
    }

    override suspend fun searchMovies(
        query: String,  page: Int
    ): BaseResponse<MovieDto> {
        return wrapApiResponse { apiService.searchMovies(query, page) }
    }

    override suspend fun searchTvShows(
        query: String, page: Int
    ): BaseResponse<TVShowDto> {
        return wrapApiResponse { apiService.searchTvShows(query, page) }
    }

    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.popularMovies()
    }

    override suspend fun getPopularTVShows(): TVShowResponse {
        return apiService.popularTVShows()
    }

    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse {
        return apiService.getTopRatedMovies(page)
    }

    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return apiService.getTopRatedSeries(page)
    }

    override suspend fun getMoviesByMoodIds(moodIds: List<Int>): MovieResponse {
        return wrapApiResponse { apiService.getMoviesByMoods(moodIds) }
    }

}