package com.berlin.repository.datasource.remote


import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TVShowResponse

interface HomeRemoteDataSource {
    suspend fun getPopularMovies(language: String) : MovieResponse
    suspend fun getPopularTVShows(language: String) : TVShowResponse
}