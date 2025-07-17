package com.berlin.remote

import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieSimilar(movieId: Long): MovieResponse {
        return client.get(ApiConstants.MOVIE_MORE_LIKE_THIS
            .replace(ApiConstants.MOVIE_ID, movieId.toString())).body()
    }
}