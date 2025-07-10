package com.berlin.remote

import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaByActorResponse
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TvShowResponse
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

    override suspend fun searchMoviesByActorName(actorName: String): MediaByActorResponse {
        return client.get(ApiConstants.SEARCH_BY_ACTOR_NAME) {
            parameter(ApiConstants.ACTOR_NAME, actorName)
        }.body()
    }

    override suspend fun searchMovies(query: String): MovieResponse {
        return client.get(BASE_URL + ApiConstants.SEARCH_MOVIE) {
            parameter(ApiConstants.QUERY, query)
        }.body()
    }

    override suspend fun searchTvShows(query: String): TvShowResponse {
        return client.get(BASE_URL + ApiConstants.SEARCH_TV) {
            parameter(ApiConstants.QUERY, query)
        }.body()
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}