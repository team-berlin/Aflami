package com.berlin.repository

import com.berlin.entity.Media
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.mapper.toMedia
import com.berlin.repository.util.QueryType
import repository.SearchRepository
import java.time.Instant

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource,
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource,
    private val categoriesPreferencesDataSource: CategoriesPreferencesDataSource,
    // sharedPref
) : SearchRepository {

    private val language = "en-US"

    override suspend fun getMoviesByCountry(
        query: String,
        page: Int
    ): List<Movie> {
        return localDataSource.getCachedSearch(query, QueryType.COUNTRY, pageSize = 20, page = page)
            .takeIf { !isExpiredOrEmpty(it) }
            ?.map { it.toDomain() }
            ?: remoteDataSource.searchMoviesByCountry(query, language, page).results
                ?.filterNotNull()
                ?.map { it.toLocal(query, QueryType.COUNTRY.name, page, "MOVIE") }
                ?.also { localDataSource.cacheSearch(it) }
                ?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getMediaByActorName(actorName: String, page: Int): List<Media> {
        val cached = localDataSource.getCachedSearch(
            actorName,
            QueryType.ACTOR,
            pageSize = 20,
            page = page
        )

        val mediaList = if (!isExpiredOrEmpty(cached)) {
            cached.map { it.toMedia() }
        } else {
            remoteDataSource.searchMoviesByActor(actorName, language, page).results
                ?.filterNotNull()
                ?.also { getActingDepartment(it) }
                ?.let { getMediaByActorName(actorName, page, it) }
                ?.also { localDataSource.cacheSearch(it) }
                ?.map { it.toMedia() }
                ?: emptyList()
        }

        return sortMediaByCategoryScore(mediaList)
    }

    private suspend fun sortMediaByCategoryScore(mediaList: List<Media>): List<Media> {
        val scores = categoriesPreferencesDataSource.getAllCategoryScores()

        return mediaList.sortedByDescending { media ->
            media.genre.sumOf { scores[it] ?: 0 }
        }
    }

    private fun getActingDepartment(listOfPersons: List<PersonDto>): List<PersonDto> {
        return listOfPersons.filter { it.knownForDepartment == ACTING_DEPARTMENT }
    }

    private fun getMediaByActorName(
        actorName: String,
        page: Int,
        listOfPersons: List<PersonDto>
    ): List<SearchingEntity> {
        return listOfPersons.flatMap {
            it.knownFor?.filterNotNull()
                ?.map {
                    it.toLocal(
                        actorName,
                        QueryType.ACTOR.name,
                        page,
                        it.mediaType
                    )
                } ?: emptyList()
        }
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

    override suspend fun getRecentSearchQueries(): List<String> {
        return recentHistoryLocalDataSource.getRecentSearchQueries()
    }

    override suspend fun saveRecentHistory(query: String) {
        val entity = SearchingEntity(
            id = query.hashCode().toLong(),
            query = query,
            type = QueryType.HISTORY.name,
            time = System.currentTimeMillis(),
            title = "",
            rating = 0.0,
            releaseYear = "",
            genre = emptyList(),
            poster = "",
            page = 1,
            mediaType = ""

        )
        recentHistoryLocalDataSource.insertQueryOnly(entity)
    }

    override suspend fun deleteQueryFromHistory(query: String) {
        recentHistoryLocalDataSource.deleteQueryFromHistory(query)
    }

    override suspend fun clearSearchHistory() {
        recentHistoryLocalDataSource.clearSearchHistory()
    }

    companion object {
        const val CACHE_TIMEOUT = 3600000L
        const val ACTING_DEPARTMENT = "Acting"
    }
}