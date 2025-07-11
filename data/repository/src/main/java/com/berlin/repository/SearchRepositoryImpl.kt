package com.berlin.repository

import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.berlin.entity.Television
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.model.toDomain
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.toLocal
import com.berlin.repository.mapper.toDomain
import repository.SearchRepository

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun searchByCountry(countryName: String, language: String): List<Movie> {
//        val searchCaching = localDataSource.getCachedSearch(countryName,language)
//        val isOneHour = searchCaching.find { it.time < System.currentTimeMillis() - 60 * 60 * 1000 }
//
//        if (searchCaching.isEmpty() && isOneHour == null) {
//            val result = remoteDataSource.searchMoviesByCountry(countryName, language).results
//                ?.filterNotNull()
//                ?.map { movieDto -> movieDto.toLocal(countryName, System.currentTimeMillis()) }
//                ?: emptyList()
//
//            localDataSource.cacheSearch(result.map { it.toLocal(countryName, System.currentTimeMillis()) })
//        }
//
//        return localDataSource.getCachedSearch(countryName,language).map { it.toDomain() }
        return emptyList()
    }

    override suspend fun searchByActorName(actorName: String): List<MediaTypeEntity> {


        val cachedList = localDataSource.getCachedSearch(actorName, "Actor").map { it.toDomain() }
        if (cachedList.isEmpty()) {
            val results = remoteDataSource.searchMoviesByActorName(actorName)
                .results
                ?.filter { it.knownForDepartment == "Acting" }
                ?.mapNotNull { it.knownFor }
                ?.flatten()
                ?.map { it.toLocal(actorName, "Actor") }
            results?.let { localDataSource.cacheSearch(it) }
        }
        val rescached = localDataSource.getCachedSearch(actorName, "Actor").map { it.toDomain() }
        return rescached

    }

    override suspend fun searchMovie(query: String): List<Movie> {
        return emptyList()
        //        remoteDataSource.searchMovies(query).results
//            ?.filterNotNull()
//            ?.map { movieDto -> movieDto.toLocal() }
//            ?: emptyList()
    }

    override suspend fun searchTvShow(query: String): List<Television> {
        return remoteDataSource.searchTvShows(query).results
            ?.filterNotNull()
            ?.map { tvShowDto -> tvShowDto.toDomain() }
            ?: emptyList()
    }


}
