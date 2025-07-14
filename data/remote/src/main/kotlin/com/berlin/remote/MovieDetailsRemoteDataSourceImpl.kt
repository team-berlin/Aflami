package com.berlin.remote

import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieCastDetails(movieId: Long): MediaCastResponse {
       return client.get(ApiConstants.MOVIE_CAST.replace("{movie_id}", movieId.toString())).body()
    }

}