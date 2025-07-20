package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse

interface SearchRemoteDataSource {
    suspend fun searchMoviesByCountry(
        countryName: String,
        language: String,
        page: Int
    ): BaseResponse<MovieDto>

    suspend fun searchMoviesByActor(
        actorName: String,
        language: String,
        page: Int
    ): BaseResponse<PersonDto>

    suspend fun searchMovies(
        query: String,
        language: String,
        page: Int
    ): BaseResponse<MovieDto>

    suspend fun searchTvShows(
        query: String,
        language: String,
        page: Int
    ): BaseResponse<TVShowDto>
}