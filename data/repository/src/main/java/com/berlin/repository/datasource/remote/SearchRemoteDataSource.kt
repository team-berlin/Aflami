package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.BaseResponse
import com.berlin.repository.datasource.remote.dto.MovieDto
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.MediaByActorResponse
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TVShowResponse

interface SearchRemoteDataSource {
    suspend fun searchMoviesByCountry(countryName: String, language: String): BaseResponse<MovieDto>
    suspend fun searchMoviesByActor(actorName: String, language: String): BaseResponse<PersonDto>
    suspend fun searchMovies(query: String, language: String): MovieResponse
    suspend fun searchTvShows(query: String, language: String): TVShowResponse
}