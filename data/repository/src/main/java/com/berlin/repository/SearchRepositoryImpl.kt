package com.berlin.repository

import com.berlin.entity.Media
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.mapper.toMedia
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
        return localDataSource.getCachedSearch(query, QueryType.COUNTRY, pageSize = 20, page = page)
            .takeIf { !isExpiredOrEmpty(it) }
            ?.map { it.toDomain() }
            ?: remoteDataSource.searchMoviesByCountry(query, language, page).results
                ?.filterNotNull()
                ?.map { it.toLocal(query, QueryType.COUNTRY, page,"MOVIE")}
                ?.also { localDataSource.cacheSearch(it) }
                ?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getMediaByActorName(actorName: String, page:Int): List<Media> {
        return localDataSource.getCachedSearch(
            actorName,
            QueryType.ACTOR,
            pageSize = 20,
            page = page
        ).takeIf { !isExpiredOrEmpty(it) }
            ?.map { it.toMedia() }
            ?:remoteDataSource.searchMoviesByActor(actorName, language,page).results
                ?.filterNotNull()
                ?.also { getActingDepartment(it) }
                ?.let { getMediaByActorName(actorName,page,it)}
                ?.also {localDataSource.cacheSearch(it) }
                ?.map { it.toMedia() }
            ?:emptyList()
    }


    private fun getActingDepartment(listOfPersons:List<PersonDto>): List<PersonDto> {
        return listOfPersons.filter { it.knownForDepartment == ACTING_DEPARTMENT }
    }

    private fun getMediaByActorName(actorName: String, page:Int, listOfPersons:List<PersonDto>): List<SearchingEntity> {
        return listOfPersons.flatMap { it.knownFor?.filterNotNull()
            ?.map { it.toLocal(
                actorName,
                QueryType.ACTOR,
                page,
                it.mediaType
            ) }?:emptyList()
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

    companion object {
        const val CACHE_TIMEOUT = 3600000L
        const val ACTING_DEPARTMENT = "Acting"
    }
}

