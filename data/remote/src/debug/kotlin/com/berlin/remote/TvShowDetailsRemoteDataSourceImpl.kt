package com.berlin.remote

import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TvShowDetailsRemoteDataSourceImpl(
    private val client: HttpClient
) : TvShowDetailsRemoteDataSource {
    override suspend fun getTvShowDetails(id: Int, language: String): TVShowDetailsDto {
        return client.get("tv/$id"){
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }
}