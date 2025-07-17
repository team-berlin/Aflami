package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.mapper.toTVShow
import repository.SearchRepository
import java.time.Instant

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    private val language = "en-US"

    override suspend fun getMoviesByCountry(countryName: String, language: String): List<Movie> {
        return emptyList()
    }

    override suspend fun getMoviesByActorName(actorName: String, language: String): List<Movie> {
        return emptyList()
    }


    override suspend fun searchMovie(query: String, page: Int): List<Movie> {
        return (localDataSource.getCachedSearch(query, QueryType.MOVIE, pageSize = 20, page = page)
            .takeIf { !isExpiredOrEmpty(it) }?.map { it.toDomain() }
            ?: remoteDataSource.searchMovies(query, language, page).results?.filterNotNull()?.map {
                it.toLocal(
                    query,
                    QueryType.MOVIE,
                )
            }?.also { localDataSource.cacheSearch(it) }?.map { it.toDomain() } ?: emptyList())
    }

    override suspend fun searchTVShow(query: String, page: Int): List<TVShow> {
        return (localDataSource.getCachedSearch(query, QueryType.TV, pageSize = 20, page = page)
            .takeIf { !isExpiredOrEmpty(it) }?.map { it.toTVShow() }
            ?: remoteDataSource.searchTvShows(query, language, page).results?.filterNotNull()?.map {
                it.toLocal(
                    query,
                    QueryType.TV,
                )
            }?.also { localDataSource.cacheSearch(it) }?.map { it.toTVShow() } ?: emptyList())
    }


    companion object {
        const val CACHE_TIMEOUT = 3600000L
    }

    private fun isExpiredOrEmpty(list: List<SearchingEntity>): Boolean {
        return list.isEmpty() || list.any { Instant.now().epochSecond - it.time > CACHE_TIMEOUT }
    }
}

