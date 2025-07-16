package com.berlin.remote

import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(id: Int, language: String): MovieDetailsDto {
        return client.get("movie/$id"){
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }

}