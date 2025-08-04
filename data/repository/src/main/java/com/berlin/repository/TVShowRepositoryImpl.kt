package com.berlin.repository

import android.util.Log
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocalEntity
import repository.TVShowRepository
import javax.inject.Inject

class TVShowRepositoryImpl @Inject constructor(
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource,
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

    override suspend fun getTopRatedSeries(page: Int): List<TVShow> {
        // Fetch top-rated series from the remote data source and map to domain model
        return remoteDataSource.getTopRatedSeries(page).results?.map { seriesDto ->
            seriesDto.toDomain(
            )
        } ?: emptyList()
    }

    override suspend fun getPopularTVShows(): List<TVShow> {
        return remoteDataSource.getPopularTVShows().results?.filterNotNull()
            ?.map { tVShowDto -> tVShowDto.toDomain() } ?: emptyList()
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