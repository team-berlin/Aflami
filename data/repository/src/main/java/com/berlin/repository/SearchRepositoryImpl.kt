package com.berlin.repository

import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.berlin.entity.Television
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.toEntity
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

    override suspend fun searchByActorName(actorName: String): List<MediaTypeEntity> {
        val listOfItems: MutableList<MediaTypeEntity> = mutableListOf()

        remoteDataSource.searchMoviesByActorName(actorName).results
            ?.filter { it.knownForDepartment == "Acting" }
            ?.forEach {
                it.knownFor?.forEach {
                    listOfItems.add(it.toEntity())
                }
            }
        return listOfItems

    }

    override suspend fun searchMovie(query: String): List<Movie> {
        return remoteDataSource.searchMovies(query).results
            ?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() }
            ?: emptyList()
    }

    override suspend fun searchTvShow(query: String): List<Television> {
        return remoteDataSource.searchTvShows(query).results
            ?.filterNotNull()
            ?.map { tvShowDto -> tvShowDto.toDomain() }
            ?: emptyList()
    }


}
