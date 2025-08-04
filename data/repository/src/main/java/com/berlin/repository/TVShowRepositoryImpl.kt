package com.berlin.repository

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toPopularTVShowEntity
import repository.TVShowRepository
import javax.inject.Inject

class TVShowRepositoryImpl @Inject constructor(
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource,
    private val homeLocalDataSource: HomeLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : TVShowRepository {

    override suspend fun getContinueWatchingTVShows(page: Int): List<TVShow> {
        return recentlyWatchedLocalDataSource.getRecentlyWatchedTvShow(page = page).map {
            it.toDomain()
        }
    }

    override suspend fun addContinueWatchingTVShow(tvShow: TVShow) {
        recentlyWatchedLocalDataSource.addRecentlyWatchedTvShow(tvShow.toLocalEntity())
    }

    override suspend fun getTopRatedTVShows(page: Int): List<TVShow> {
        return remoteDataSource.getTopRatedSeries(page).results?.map { seriesDto ->
            seriesDto.toDomain(
            )
        } ?: emptyList()
    }

    override suspend fun getPopularTVShows(): List<TVShow> {
        val localTVShows = homeLocalDataSource.getTVShows()
        if (localTVShows.isNotEmpty()) {
            return localTVShows.map { it.toDomain() }
        }
        val remoteTVShows = remoteDataSource.getPopularTVShows().results
            ?.map { it.toDomain() } ?: emptyList()
        if (remoteTVShows.isNotEmpty()) {
            homeLocalDataSource.addTVShows(remoteTVShows.map { it.toPopularTVShowEntity() })
        }
        return remoteTVShows
    }

    override suspend fun searchTVShow(
        query: String,
        page: Int,
    ): List<TVShow> {
        return remoteDataSource.getTvShowsByKeyword(query, page).results?.filterNotNull()?.map {
            it.toDomain()
        } ?: emptyList()
    }

    override suspend fun getRecentTVShowsSearchQueries(): List<String> {
        return recentHistoryLocalDataSource.getRecentSearchQueries()
    }

    override suspend fun saveRecentTVShowsHistory(query: String) {
        val entity = SearchingEntity(
            query = query,
            type = QueryType.HISTORY.name,
            queryType = QueryType.TV,

            )
        recentHistoryLocalDataSource.insertQueryOnly(entity)
    }

    override suspend fun deleteTVShowQueryFromHistory(query: String) {
        recentHistoryLocalDataSource.deleteQueryFromHistory(query)
    }

    override suspend fun clearTVShowSearchHistory() {
        recentHistoryLocalDataSource.clearSearchHistory()
    }

}