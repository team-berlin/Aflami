package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.util.ActingDepartment
import com.berlin.repository.util.QueryType
import repository.SearchRepository

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun getMoviesByCountry(countryName: String, language: String): List<Movie> {
        val searchCaching = localDataSource.getCachedSearch(countryName, QueryType.COUNTRY.name)
        val oneHourPassed = searchCaching.find {
            it.time < (System.currentTimeMillis() + ONE_HOUR_IN_MILLIS)
        } == null

        if (searchCaching.isEmpty() || oneHourPassed) {
            val result = remoteDataSource.searchMoviesByCountry(
                countryName,
                language
            ).results?.filterNotNull()?.map { movieDto ->
                    movieDto.toLocal(
                        countryName, System.currentTimeMillis(), QueryType.COUNTRY.name
                    )
                } ?: emptyList()

            localDataSource.cacheSearch(result)
        }

        return localDataSource.getCachedSearch(countryName, QueryType.COUNTRY.name).map { it.toDomain() }
    }

    override suspend fun getMoviesByActorName(actorName: String, language: String): List<Movie> {
        val searchCaching = localDataSource.getCachedSearch(actorName, QueryType.ACTOR.name)
        val isCacheStale =
            searchCaching.any { it.time < System.currentTimeMillis() - 60 * 60 * 1000 }

        if (searchCaching.isEmpty() || isCacheStale) {
            val result =
                remoteDataSource.searchMoviesByActor(actorName, language).results?.filterNotNull()
                    ?.filter { it.knownForDepartment == ActingDepartment }?.flatMap { person ->
                        person.knownFor?.filterNotNull()?.map {
                                it.toLocal(
                                    query = actorName,
                                    type = QueryType.ACTOR.name,
                                    time = System.currentTimeMillis()
                                )
                            } ?: emptyList()
                    } ?: emptyList()
            localDataSource.cacheSearch(result)
        }

        return localDataSource.getCachedSearch(actorName, QueryType.ACTOR.name).map { it.toDomain() }
    }


    override suspend fun searchMovie(query: String, language: String): List<Movie> {
        return remoteDataSource.searchMovies(query, language).results?.filterNotNull()
            ?.map { movieDto -> movieDto.toDomain() } ?: emptyList()
    }

    override suspend fun searchTVShow(query: String, language: String): List<TVShow> {
        return remoteDataSource.searchTvShows(query, language).results?.filterNotNull()
            ?.map { tvShowDto -> tvShowDto.toDomain() } ?: emptyList()
    }

    companion object {
        const val ONE_HOUR_IN_MILLIS = 3600000L
    }
}

