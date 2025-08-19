package com.berlin.repository

import com.berlin.entity.TVShow
import com.berlin.exception.NetworkException
import com.berlin.repository.datasource.local.datasource.HomeLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.HomeSection
import com.berlin.repository.datasource.local.dto.RecentSearchHistoryEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toPopularTVShowEntity
import com.berlin.repository.mapper.toTopRateTVShowEntity
import repository.TVShowRepository
import javax.inject.Inject

class TVShowRepositoryImpl @Inject constructor(
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val recentHistoryLocalDataSource: RecentHistoryLocalDataSource,
    private val homeLocalDataSource: HomeLocalDataSource,
    private val remoteDataSource: RemoteDataSource
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
        return try {
            val remoteTVShows = remoteDataSource.getTopRatedTV(page)
                .results?.map { it.toDomain() }.orEmpty()

            if (remoteTVShows.isNotEmpty()) {
                homeLocalDataSource.addTVShows(
                    remoteTVShows.map { it.toTopRateTVShowEntity() }
                )
            }

            remoteTVShows.ifEmpty {
                homeLocalDataSource.getTVShowsBySection(HomeSection.TOP_RATING)
                    .map { it.toDomain() }
            }
        } catch (e: NetworkException) {
            homeLocalDataSource.getTVShowsBySection(HomeSection.TOP_RATING)
                .map { it.toDomain() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPopularTVShows(): List<TVShow> {
        return try {
            val remoteTVShows = remoteDataSource.getPopularTVShows()
                .results?.map { it.toDomain() }.orEmpty()

            if (remoteTVShows.isNotEmpty()) {
                homeLocalDataSource.addTVShows(remoteTVShows.map { it.toPopularTVShowEntity() })
            }

            remoteTVShows.ifEmpty {
                homeLocalDataSource.getTVShowsBySection(HomeSection.POPULAR)
                    .map { it.toDomain() }
            }
        } catch (e: NetworkException) {
            homeLocalDataSource.getTVShowsBySection(HomeSection.POPULAR)
                .map { it.toDomain() }
        } catch (e: Exception) {
            throw e
        }
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
        } .orEmpty()
    }

    override suspend fun getRecentTVShowsSearchQueries(): List<String> {
        return recentHistoryLocalDataSource.getRecentSearchQueries()
    }

    override suspend fun saveRecentTVShowsHistory(query: String) {
        val entity = RecentSearchHistoryEntity(
            query = query,
            type = QueryType.HISTORY,
            time = System.currentTimeMillis(),
        )
        recentHistoryLocalDataSource.insertQueryOnly(entity)
    }

    override suspend fun deleteTVShowQueryFromHistory(query: String) {
        recentHistoryLocalDataSource.deleteQueryFromHistory(query)
    }

    override suspend fun clearTVShowSearchHistory() {
        recentHistoryLocalDataSource.clearSearchHistory()
    }

    override suspend fun getTVShowsByCategory(
        genreId: Long,
        page: Int
    ): List<TVShow> {
       return remoteDataSource.getTvShowsByCategory(genreId, page).results?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun getTVShowGame(): List<TVShow> {
       return remoteDataSource.getTVShow().results?.map {
            it.toDomain()
        }.orEmpty()
    }

}