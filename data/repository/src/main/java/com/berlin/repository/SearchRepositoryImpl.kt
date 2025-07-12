package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
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

    override suspend fun searchByActorName(actorName: String): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(query: String): List<Movie> {
        return remoteDataSource.searchMovies(query).results
            ?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() }
            ?: emptyList()
    }

    override suspend fun searchTvShow(query: String): List<TVShow> {
        return remoteDataSource.searchTvShows(query).results
            ?.filterNotNull()
            ?.map { tvShowDto -> tvShowDto.toDomain() }
            ?: emptyList()
    }


}