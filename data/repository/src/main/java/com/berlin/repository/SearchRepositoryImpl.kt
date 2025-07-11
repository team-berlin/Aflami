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
        return remoteDataSource.searchMoviesByCountry(countryName).results?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() } ?: emptyList()
    }

    override suspend fun searchByActorName(actorName: String): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(query: String, language: String): List<Movie> {
        return remoteDataSource.searchMovies(query, language).results?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() } ?: emptyList()
    }

    override suspend fun searchTVShow(query: String, language: String): List<TVShow> {
        return remoteDataSource.searchTvShows(query, language).results?.filterNotNull()
            ?.map { tvShowDto -> tvShowDto.toDomain() } ?: emptyList()
    }
}