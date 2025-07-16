package com.berlin.remote

import com.berlin.repository.datasource.local.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieDetailsRemoteDataSourceImpl
    (private val ktorClient: HttpClient) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        return ktorClient.get(
            ApiConstants.MOVIE_IMAGES
                .replace("id", "$movieId")
        ) {}.body<MediaImagesResponse>()
    }
}