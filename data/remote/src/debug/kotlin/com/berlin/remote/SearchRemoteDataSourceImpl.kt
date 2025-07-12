package com.berlin.remote

import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchRemoteDataSourceImpl(
    private val client: HttpClient
) : SearchRemoteDataSource {
    override suspend fun searchMoviesByCountry(
        countryName: String,
        language: String
    ): BaseResponse<MovieDto> {
        return client.get(APIConstants.SEARCH_BY_COUNTRY) {
            parameter(APIConstants.WITH_ORIGIN_COUNTRY, countryName)
            parameter(APIConstants.LANGUAGE, language)
        }.body()
    }

    override suspend fun searchMoviesByActor(actorName: String, language: String): BaseResponse<PersonDto> {
        return client.get(APIConstants.SEARCH_BY_ACTOR) {
            parameter(APIConstants.QUERY, actorName)
            parameter(APIConstants.LANGUAGE, language)
        }.body()
    }
}