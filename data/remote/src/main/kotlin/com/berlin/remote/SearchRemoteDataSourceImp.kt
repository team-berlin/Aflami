package com.berlin.remote

import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchRemoteDataSourceImp(
    private val client: HttpClient
) : SearchRemoteDataSource {
    override suspend fun searchMoviesByCountry(
        countryName: String, language: String, page: Int
    ): BaseResponse<MovieDto> {
        return client.get(ApiConstants.SEARCH_BY_COUNTRY) {
            parameter(ApiConstants.WITH_ORIGIN_COUNTRY, countryName)
            parameter(ApiConstants.LANGUAGE, language)
            parameter(ApiConstants.PAGE, page)
        }.body()
    }

    override suspend fun searchMoviesByActor(
        actorName: String, language: String, page: Int
    ): BaseResponse<PersonDto> {
        return client.get(ApiConstants.SEARCH_BY_ACTOR) {
            parameter(ApiConstants.QUERY, actorName)
            parameter(ApiConstants.LANGUAGE, language)
            parameter(ApiConstants.PAGE, page)
        }.body()
    }


    override suspend fun searchMovies(
        query: String, language: String, page: Int
    ): BaseResponse<MovieDto> {
        return client.get(ApiConstants.SEARCH_MOVIE) {
            parameter(ApiConstants.QUERY, query)
            parameter(ApiConstants.LANGUAGE, language)
            parameter(ApiConstants.PAGE, page)
        }.body()
    }

    override suspend fun searchTvShows(
        query: String, language: String, page: Int
    ): BaseResponse<TVShowDto> {
        return client.get(ApiConstants.SEARCH_TV) {
            parameter(ApiConstants.QUERY, query)
            parameter(ApiConstants.LANGUAGE, language)
            parameter(ApiConstants.PAGE, page)
        }.body()
    }
}