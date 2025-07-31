package com.berlin.repository

import com.berlin.entity.TVShow
import com.berlin.repository.MediaType.TV_SHOW
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocal
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toTVShow
import repository.TVShowRepository

class TVShowRepositoryImpl(
    private val localDataSource: ContinueWatchingLocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : TVShowRepository {

    override suspend fun getContinueWatchingTVShows(): List<TVShow> {
        return localDataSource.getContinueWatchedTVShow().map {
            it.toTVShow()
        }
    }

    override suspend fun addContinueWatchingTVShow(tvShow: TVShow) {
        localDataSource.addContinueWatchedTVShow(tvShow.toLocalEntity())
    }

    override suspend fun getTopRatedSeries(page: Int): List<TVShow> {
        return remoteDataSource.getTopRatedSeries(page).topRatedSeries.map { seriesDto -> seriesDto.toTVShow() }
    }

    override suspend fun getPopularTVShows(language: String): List<javax.print.attribute.standard.Media> {
        return remoteDataSource.getPopularTVShows(language).results?.filterNotNull()
            ?.map { tVShowDto -> tVShowDto.toDomain(TV_SHOW) } ?: emptyList()    }

    override suspend fun searchTVShow(
        query: String,
        page: Int
    ): List<TVShow> {
        return (localDataSource.getCachedSearch(query, QueryType.TV, pageSize = 20, page = page)
            .takeIf { !isExpiredOrEmpty(it) }?.map { it.toTVShow() }
            ?: remoteDataSource.searchTvShows(query, language, page).results?.filterNotNull()?.map {
                it.toLocal(
                    query, QueryType.TV.name, page, "TVShow"
                )
            }?.also { localDataSource.cacheSearch(it) }?.map { it.toTVShow() } ?: emptyList())
    }

    override suspend fun getRecentTVShowsSearchQueries(): List<String> {
        return localDataSource.getRecentSearchQueries()
    }

    override suspend fun saveRecentTVShowsHistory(query: String) {
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
        localDataSource.insertQueryOnly(entity)
    }

    override suspend fun deleteTVShowQueryFromHistory(query: String) {
        localDataSource.deleteQueryFromHistory(query)
    }

    override suspend fun clearTVShowSearchHistory() {
        localDataSource.clearSearchHistory()
    }

}