package com.berlin.remote

import com.berlin.remote.network.SearchApiService
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse

class SearchRemoteDataSourceImpl(
    private val searchApiService: SearchApiService
) : SearchRemoteDataSource {

    override suspend fun searchMoviesByCountry(
        countryName: String, language: String, page: Int
    ): BaseResponse<MovieDto> {
        return searchApiService.searchMoviesByCountry(countryName, language, page)
    }

    override suspend fun searchMoviesByActor(
        actorName: String, language: String, page: Int
    ): BaseResponse<PersonDto> {
        return searchApiService.searchMoviesByActor(actorName, language, page)
    }

    override suspend fun searchMovies(query: String, language: String,page: Int): BaseResponse<MovieDto> {
        return searchApiService.searchMovies(query, language,page)
    }

    override suspend fun searchTvShows(query: String, language: String,page: Int): BaseResponse<TVShowDto> {
        return searchApiService.searchTvShows(query, language,page)
    }
}
