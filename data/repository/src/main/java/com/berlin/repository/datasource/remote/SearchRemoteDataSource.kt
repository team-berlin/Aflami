package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto

interface SearchRemoteDataSource {
    suspend fun searchMoviesByCountry(countryName: String, language: String): BaseResponse<MovieDto>
}