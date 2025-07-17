package com.berlin.remote

import android.util.Log
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.berlin.repository.datasource.remote.dto.MovieDetailsDto
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.MovieResponse

class MovieDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieCastDetails(movieId: Long, language: String): MediaCastResponse {
        return client.get(ApiConstants.MOVIE_CAST.replace("{movie_id}", movieId.toString())) {
            parameter("language", language)
        }.body<MediaCastResponse>()
    }
    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        return client.get(
            ApiConstants.MOVIE_IMAGES
                .replace("id", "$movieId")
        ) {}.body<MediaImagesResponse>()
    }

    override suspend fun getMovieDetails(id: Long, language: String): MovieDetailsDto {
        return client.get("movie/$id") {
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }
    override suspend fun getMovieSimilar(movieId: Long): MovieResponse {
        return client.get(ApiConstants.MOVIE_MORE_LIKE_THIS
            .replace(ApiConstants.MOVIE_ID, movieId.toString())).body()
    }

}