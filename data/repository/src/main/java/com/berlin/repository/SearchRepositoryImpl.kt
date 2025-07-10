package com.berlin.repository

import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaItem
import com.berlin.repository.datasource.remote.dto.toEntity
import com.berlin.repository.mapper.toDomain
import repository.SearchRepository

class SearchRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun searchByCountry(countryName: String): List<Movie> {
        return remoteDataSource.searchMoviesByCountry(countryName).results
            ?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() }
            ?: emptyList()
    }

    override suspend fun searchByActorName(actorName: String): List<MediaTypeEntity> {
        return remoteDataSource.searchMoviesByActorName(actorName).results
            ?.knownFor
            ?.filterNotNull()
            ?.map {it.toEntity() }
            ?:emptyList()

    }
}