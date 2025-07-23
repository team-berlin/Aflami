package com.berlin.remote

import com.berlin.remote.network.MovieApiService
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.ReviewResponse

class MovieDetailsRemoteDataSourceImpl(
    private val movieApi: MovieApiService
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieSimilar(movieId: Long): MovieResponse {
        return movieApi.getMovieSimilar(movieId)
    }

    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        return movieApi.getMovieImages(movieId)
    }

    override suspend fun getMovieDetails(movieId: Long, language: String): MovieDetailsDto {
        return movieApi.getMovieDetails(movieId, language)

    }

    override suspend fun getMovieCastDetails(movieId: Long, language: String): MediaCastResponse {
        return movieApi.getMovieCastDetails(movieId, language)

    }

    override suspend fun getReviews(movieId: Long): ReviewResponse {
        return movieApi.getMovieReviews(movieId)
    }

}
