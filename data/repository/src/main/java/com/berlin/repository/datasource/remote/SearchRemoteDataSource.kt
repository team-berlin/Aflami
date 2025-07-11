package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaByActorResponse
import com.berlin.repository.datasource.remote.dto.MovieResponse
import com.berlin.repository.datasource.remote.dto.TvShowResponse

interface SearchRemoteDataSource {
    suspend fun searchMoviesByCountry(countryName: String, language: String): MovieResponse
    suspend fun searchMoviesByCountry(countryName: String): MovieResponse
    suspend fun searchMoviesByActorName(actorName: String): MediaByActorResponse
    suspend fun searchMovies(query: String): MovieResponse
    suspend fun searchTvShows(query: String): TvShowResponse
}