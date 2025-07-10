package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MovieResponse

interface SearchRemoteDataSource {
    suspend fun searchMoviesByCountry(countryName: String, language: String): MovieResponse
}