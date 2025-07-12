package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto

interface SearchRemoteDataSource {
    suspend fun searchMoviesByCountry(countryName: String, language: String): BaseResponse<MovieDto>
    suspend fun searchMoviesByActor(actorName: String, language: String): BaseResponse<PersonDto>
}