package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import repository.SearchRepository

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun searchByCountry(countryName: String, language: String): List<Movie> {
        val searchCaching = localDataSource.getCachedSearch(countryName)
        val isOneHour = searchCaching.find { it.time < System.currentTimeMillis() - 60 * 60 * 1000 }

        if (searchCaching.isEmpty() && isOneHour == null) {
            val result = remoteDataSource.searchMoviesByCountry(countryName, language).results
                ?.filterNotNull()
                ?.map { movieDto -> movieDto.toLocal(countryName, System.currentTimeMillis()) }
                ?: emptyList()

            localDataSource.cacheSearch(result)
        }

        return localDataSource.getCachedSearch(countryName).map { it.toDomain() }
    }
}
