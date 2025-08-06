package com.berlin.repository

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

    override suspend fun getTopRatedTVShows(page: Int): List<TVShow> {
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference()
            .associate { it.categoryId to it.count }
        return remoteDataSource.getTopRatedTV(page).results?.map { seriesDto ->
            seriesDto.toDomain(
            )
        }?.sortedByDescending { tvShow ->
            tvShow.genres.sumOf { genre ->
                genreScoresMap[genre.id] ?: 0
            }
        } ?: emptyList()
    }

    override suspend fun getPopularTVShows(): List<TVShow> {
        val genreScoresMap = recentlyWatchedLocalDataSource
            .getCategoryAsPreference()
            .associate { it.categoryId to it.count }

        return remoteDataSource.getPopularTVShows()
            .results
            ?.map { tVShowDto -> tVShowDto.toDomain() }
            ?.sortedByDescending { tvShow-> tvShow.genres.sumOf { genre-> genreScoresMap[genre.id]?:0 } }
     ?: emptyList()
    }

    override suspend fun searchTVShow(
        query: String,
        page: Int,
    ): List<TVShow> {
        val genreScoresMap = recentlyWatchedLocalDataSource
            .getCategoryAsPreference()
            .associate { it.categoryId to it.count }

        return remoteDataSource.getTVShowsByKeyword(query, page).results?.map {
            it.toDomain()
        }?.sortedByDescending { tvShow ->
            tvShow.genres.sumOf { genre ->
                genreScoresMap[genre.id] ?:0
            }
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