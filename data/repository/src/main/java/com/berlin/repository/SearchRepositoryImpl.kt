package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.SearchRepository

class SearchRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun searchByCountry(countryName: String, language: String): List<Movie> {
        return remoteDataSource.searchMoviesByCountry(countryName, language).results
            ?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() }
            ?: emptyList()
    }
}