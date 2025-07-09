package com.berlin.remote

import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchRemoteDataSourceImpl(
    private val client: HttpClient
) : SearchRemoteDataSource {
    override suspend fun searchMoviesByCountry(countryName: String): MovieResponse {
        return client.get(ApiConstants.SEARCH_BY_COUNTRY) {
            parameter(ApiConstants.WITH_ORIGIN_COUNTRY, countryName)
        }.body()
    }
}