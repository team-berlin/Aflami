package com.berlin.remote

import android.util.Log
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieDetailsRemoteDataSourceImpl
    (private val ktorClient: HttpClient) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieImages(movieId: Long): MediaImagesResponse {
        Log.d("Khairy", "getMovieImages from remote data source...")
        return ktorClient.get(
            ApiConstants.MOVIE_IMAGES
                .replace("id", "$movieId")
        ) {}.body<MediaImagesResponse>()
    }
}