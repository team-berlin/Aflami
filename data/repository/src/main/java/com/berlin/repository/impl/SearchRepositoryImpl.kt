package com.berlin.repository.impl

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import repository.SearchRepository
import java.time.Instant

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource,
    // sharedPref
) : SearchRepository {

    private val language = "en-US"

    override suspend fun getMoviesByCountry(
        query: String,
        page: Int
    ): List<Movie> {
        val movies = localDataSource.getCachedSearch(query, QueryType.COUNTRY, page = page)
        if (!isExpiredOrEmpty(movies)) return movies.map { it.toDomain() }

        remoteDataSource.searchMoviesByCountry(query, language, page).results
            ?.filterNotNull()
            ?.map { it.toLocal(query, QueryType.COUNTRY) }
            ?.also { localDataSource.cacheSearch(it) }

        return localDataSource.getCachedSearch(query, QueryType.COUNTRY, page = page)
            .map { it.toDomain() }
    }

    override suspend fun getMoviesByActorName(actorName: String, language: String): List<Movie> {
//        val searchCaching = localDataSource.getCachedSearch(actorName, QueryType.ACTOR.name)
//        val isCacheStale =
//            searchCaching.any { it.time < System.currentTimeMillis() - 60 * 60 * 1000 }
//
//        if (searchCaching.isEmpty() || isCacheStale) {
//            val result =
//                remoteDataSource.searchMoviesByActor(actorName, language).results?.filterNotNull()
//                    ?.filter { it.knownForDepartment == ActingDepartment }?.flatMap { person ->
//                        person.knownFor?.filterNotNull()?.map {
//                            it.toLocal(
//                                query = actorName,
//                                type = QueryType.ACTOR.name,
//                                time = System.currentTimeMillis()
//                            )
//                        } ?: emptyList()
//                    } ?: emptyList()
//            localDataSource.cacheSearch(result)
//        }
//
//        return localDataSource.getCachedSearch(actorName, QueryType.ACTOR.name)
//            .map { it.toDomain() }
        return emptyList()
    }


    override suspend fun searchMovie(query: String, language: String): List<Movie> {
        return emptyList()
//        val searchCaching = localDataSource.getCachedSearch(query, QueryType.MOVIE.name)
//        val oneHourPassed = searchCaching.find {
//            it.time < (System.currentTimeMillis() + ONE_HOUR_IN_MILLIS)
//        } == null
//        if (searchCaching.isEmpty() || oneHourPassed) {
//            val result = remoteDataSource.searchMovies(query, language).results?.filterNotNull()
//                ?.map { movieDto ->
//                    movieDto.toLocal(
//                        query, System.currentTimeMillis(), QueryType.MOVIE.name
//                    )
//                } ?: emptyList()
//            localDataSource.cacheSearch(result)
//        }
//        return localDataSource.getCachedSearch(query, QueryType.MOVIE.name).map {
//            it.toDomain()
//        }
    }

    override suspend fun searchTVShow(query: String, language: String): List<TVShow> {
//        val searchCaching = localDataSource.getCachedSearch(query, QueryType.TV.name)
//        val isCacheStale =
//            searchCaching.any { it.time < System.currentTimeMillis() - ONE_HOUR_IN_MILLIS }
//
//        if (searchCaching.isEmpty() || isCacheStale) {
//            val result = remoteDataSource.searchTvShows(query, language).results?.filterNotNull()
//                ?.map { tvShowDto ->
//                    tvShowDto.toLocal(
//                        query = query, type = QueryType.TV.name, time = System.currentTimeMillis()
//                    )
//                } ?: emptyList()
//
//            localDataSource.cacheSearch(result)
//        }
//
//        return localDataSource.getCachedSearch(query, QueryType.TV.name).map { it.toTVShow() }
        return emptyList()
    }

    private fun isExpiredOrEmpty(list: List<SearchingEntity>): Boolean {
        return list.isEmpty() ||
                list.any { Instant.now().epochSecond - it.time > CACHE_TIMEOUT }
    }

    companion object {
        const val CACHE_TIMEOUT = 3600000L
        const val ACTING_DEPARTMENT = "Acting"
    }
}