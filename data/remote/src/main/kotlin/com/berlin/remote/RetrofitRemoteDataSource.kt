package com.berlin.remote

import com.berlin.remote.network.ApiService
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.FavouriteListDto
import com.berlin.repository.datasource.remote.dto.FavouriteListItem
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.request.CreateListRequest
import com.berlin.repository.datasource.remote.dto.request.RemoveMovieFromListRequest
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import javax.inject.Inject

class RetrofitRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val authenticationLocalDataSource: AuthenticationLocalDataSource,
) : RemoteDataSource {


    override suspend fun getSimilarMovies(movieId: Long): BaseResponse<MovieDetailsDto> {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieSimilar(movieId) }
    }

    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieImages(movieId) }
    }

    override suspend fun getTVImagesById(seriesId: Long): MediaImagesResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVImages(seriesId) }
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetailsDto {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieDetails(movieId) }
    }

    override suspend fun getMovieCastDetails(movieId: Long): MediaCastResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieCastDetails(movieId) }
    }

    override suspend fun getMovieReviews(movieId: Long): BaseResponse<ReviewDto> {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieReviews(movieId) }
    }

    override suspend fun getUpComingMovies(selectedGenres: Int): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.getUpcomingMovies(selectedGenres) }
    }

    override suspend fun getTVShowDetailsById(seriesId: Long): TVShowDetailsDto {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVShowDetails(seriesId) }
    }

    override suspend fun getTVCastDetailsById(seriesId: Long): MediaCastResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVCastDetails(seriesId) }
    }

    override suspend fun getSimilarTVById(seriesId: Long): BaseResponse<TVShowDetailsDto> {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVSimilar(seriesId) }
    }

    override suspend fun getTVShowReviewsById(seriesId: Long): BaseResponse<ReviewDto> {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVReviews(seriesId) }
    }

    override suspend fun getEpisodeSeasonTV(seriesId: Long, seasonNumber: Int): SeasonEpisodesDto {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        require(seasonNumber >= 0) { "Invalid seasonNumber: $seasonNumber" }
        return wrapApiResponse { apiService.getEpisodeSeasonSeries(seriesId, seasonNumber) }
    }

    override suspend fun getMovieGenres(): GenreResponse {
        return wrapApiResponse { apiService.getMovieGenres() }
    }

    override suspend fun getTVGenres(): GenreResponse {
        return wrapApiResponse { apiService.getTVGenres() }
    }

    override suspend fun getMoviesByCountryName(
        countryName: String,
        page: Int,
    ): BaseResponse<MovieDetailsDto> {
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMoviesByCountry(countryName, page) }
    }

    override suspend fun getMoviesByActorName(
        actorName: String,
        page: Int,
    ): BaseResponse<PersonDto> {
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMoviesByActor(actorName, page) }
    }

    override suspend fun getMoviesByKeyword(
        query: String,
        page: Int,
    ): BaseResponse<MovieDetailsDto> {
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchMovies(query, page) }
    }

    override suspend fun getTVShowsByKeyword(
        query: String,
        page: Int,
    ): BaseResponse<TVShowDetailsDto> {
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.searchTVShows(query, page) }
    }

    override suspend fun getPopularMovies(): BaseResponse<MovieDetailsDto> {
        return wrapApiResponse { apiService.popularMovies() }
    }

    override suspend fun getPopularTVShows(): BaseResponse<TVShowDetailsDto> {
        return wrapApiResponse { apiService.popularTVShows() }
    }

    override suspend fun getTopRatedMovies(page: Int): BaseResponse<MovieDetailsDto> {
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.getTopRatedMovies(page) }
    }

    override suspend fun getTopRatedTV(page: Int): BaseResponse<TVShowDetailsDto> {
        require(page > 0) { "Page must be greater than 0" }
        return wrapApiResponse { apiService.getTopRatedSeries(page) }
    }

    override suspend fun getMoviesByMoodIds(moodIds: List<Int>): BaseResponse<MovieDetailsDto> {
        require(moodIds.isNotEmpty()) { "Mood IDs cannot be empty" }
        return wrapApiResponse { apiService.getMoviesByMoods(moodIds) }
    }

    override suspend fun getMovieVideos(movieId: Long): VideosResponse {
        require(movieId > 0) { "Invalid movieId: $movieId" }
        return wrapApiResponse { apiService.getMovieVideos(movieId) }
    }

    override suspend fun getTVShowVideos(seriesId: Long): VideosResponse {
        require(seriesId > 0) { "Invalid seriesId: $seriesId" }
        return wrapApiResponse { apiService.getTVShowVideos(seriesId) }
    }

    override suspend fun getUserFavouriteLists(): List<FavouriteListDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFavouriteListItems(
        pageNumber: Int,
        favouriteListId: Int,
    ): List<FavouriteListItem> {
        return wrapApiResponse {
            apiService.getFavouriteMoviesFromList(
                listId = favouriteListId,
                pageNumber = pageNumber
            )
        }.favouriteListItems
    }

    override suspend fun deleteUserFavouriteList(listId: Int) {
        wrapApiResponse {
            apiService.deleteUserFavouriteList(
                sessionId = authenticationLocalDataSource.getUserSessionId()
                    ?: throw IllegalStateException("userSessionID == null"),
                favouriteListId = listId
            )
        }
    }

    override suspend fun deleteMovieFromUserFavouriteList(
        listId: Int,
        movieId: Long,
    ) {
        wrapApiResponse {
            apiService.deleteMovieFromList(
                listId = listId,
                sessionId = authenticationLocalDataSource.getUserSessionId()
                    ?: throw IllegalStateException("userSessionID == null"),
                removeMovieFromListRequest = RemoveMovieFromListRequest(movieId = movieId)
            )
        }
    }

    override suspend fun createNewFavouriteList(title: String) {
        wrapApiResponse {
            apiService.createNewFavouriteList(
                sessionId = authenticationLocalDataSource.getUserSessionId()
                    ?: throw IllegalStateException("userSessionID == null"),
                createListRequest = CreateListRequest(name = title, description = "", language = "")
            )
        }
    }
}