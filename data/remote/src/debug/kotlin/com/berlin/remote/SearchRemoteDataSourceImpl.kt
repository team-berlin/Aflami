package com.berlin.remote

import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.MediaByActorResponse
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TVShowResponse
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
        return client.get(ApiConstants.SEARCH_BY_COUNTRY) {
            parameter(ApiConstants.WITH_ORIGIN_COUNTRY, countryName)
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }

    override suspend fun searchMoviesByActor(actorName: String, language: String): BaseResponse<PersonDto> {
        return client.get(ApiConstants.SEARCH_BY_ACTOR) {
            parameter(ApiConstants.QUERY, actorName)
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }



    override suspend fun searchMovies(query: String, language: String): MovieResponse {
        return client.get(ApiConstants.SEARCH_MOVIE) {
            parameter(ApiConstants.QUERY, query)
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }

    override suspend fun searchTvShows(query: String, language: String): TVShowResponse {
        return client.get(ApiConstants.SEARCH_TV) {
            parameter(ApiConstants.QUERY, query)
            parameter(ApiConstants.LANGUAGE, language)
        }.body()
    }
}