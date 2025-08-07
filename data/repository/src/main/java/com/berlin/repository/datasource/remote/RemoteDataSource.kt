package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.CreateListResponse
import com.berlin.repository.datasource.remote.dto.DeleteListResponse
import com.berlin.repository.datasource.remote.dto.FavouriteListDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.ReviewDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDto
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse

interface RemoteDataSource {
    suspend fun getSimilarMovies(movieId: Long): BaseResponse<MovieDetailsDto>
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getTVImagesById(seriesId: Long): MediaImagesResponse
    suspend fun getMovieDetails(movieId: Long): MovieDetailsDto
    suspend fun getMovieCastDetails(movieId: Long): MediaCastResponse
    suspend fun getMovieReviews(movieId: Long): BaseResponse<ReviewDto>
    suspend fun getUpComingMovies(selectedGenres: Int): BaseResponse<MovieDetailsDto>
    suspend fun getTVShowDetailsById(seriesId: Long): TVShowDetailsDto
    suspend fun getTVCastDetailsById(seriesId: Long): MediaCastResponse
    suspend fun getSimilarTVById(seriesId: Long): BaseResponse<TVShowDetailsDto>
    suspend fun getTVShowReviewsById(seriesId: Long): BaseResponse<ReviewDto>
    suspend fun getEpisodeSeasonTV(seriesId: Long, seasonNumber: Int): SeasonEpisodesDto
    suspend fun getMovieGenres(): GenreResponse
    suspend fun getTVGenres(): GenreResponse
    suspend fun getMoviesByCountryName(
        countryName: String,
        page: Int,
    ): BaseResponse<MovieDetailsDto>

    suspend fun getMoviesByActorName(actorName: String, page: Int): BaseResponse<PersonDto>
    suspend fun getMoviesByKeyword(query: String, page: Int): BaseResponse<MovieDetailsDto>
    suspend fun getTVShowsByKeyword(query: String, page: Int): BaseResponse<TVShowDetailsDto>
    suspend fun getPopularMovies(): BaseResponse<MovieDetailsDto>
    suspend fun getPopularTVShows(): BaseResponse<TVShowDetailsDto>
    suspend fun getTopRatedMovies(page: Int): BaseResponse<MovieDetailsDto>
    suspend fun getTopRatedTV(page: Int): BaseResponse<TVShowDetailsDto>
    suspend fun getMoviesByMoodIds(moodIds: List<Int>): BaseResponse<MovieDetailsDto>
    suspend fun getMovieVideos(movieId: Long): VideosResponse
    suspend fun getTVShowVideos(seriesId: Long): VideosResponse

    suspend fun getUserFavouriteLists(): List<FavouriteListDto>
    suspend fun getUserFavouriteListItems(
        pageNumber: Int,
        favouriteListId: Int,
    ): List<MovieDto>

    suspend fun deleteUserFavouriteList(listId: Int): DeleteListResponse
    suspend fun deleteMovieFromUserFavouriteList(listId: Int, movieId: Long)
    suspend fun createNewFavouriteList(title: String): CreateListResponse
}