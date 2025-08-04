package com.berlin.local.datasource

import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import javax.inject.Inject

class RecentlyWatchedLocalDataSourceImpl @Inject constructor(
    private val continueWatchingDao: ContinueWatchingDao
) : RecentlyWatchedLocalDataSource {
    override suspend fun getRecentlyWatchedMovie(
        pageSize: Int, page: Int
    ): List<RecentlyWatchedMovieEntity> {
        return continueWatchingDao.getContinueWatchingMovies(
            pageSize = pageSize, skip = (page - 1) * 20
        )
    }

    override suspend fun addRecentlyWatchedMovie(movieEntity: RecentlyWatchedMovieEntity) {
        continueWatchingDao.addContinueWatchingMovie(movieEntity)

    }

    override suspend fun getRecentlyWatchedTvShow(
        pageSize: Int, page: Int
    ): List<RecentlyWatchedTvShowEntity> {
        return continueWatchingDao.getContinueWatchingTVShows(
            pageSize = pageSize, skip = (page - 1) * 20
        )
    }

    override suspend fun addRecentlyWatchedTvShow(tvShowEntity: RecentlyWatchedTvShowEntity) {
        continueWatchingDao.addContinueWatchingTVShow(tvShowEntity)

    }

}