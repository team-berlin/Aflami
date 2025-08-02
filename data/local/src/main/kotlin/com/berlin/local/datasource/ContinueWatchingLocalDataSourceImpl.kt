package com.berlin.local.datasource

import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity
import javax.inject.Inject

class ContinueWatchingLocalDataSourceImpl  @Inject constructor (
    private val continueWatchingDao: ContinueWatchingDao
) : ContinueWatchingLocalDataSource {
    override suspend fun getContinueWatchingMovie( pageSize: Int, page: Int): List<ContinueWatchingMovieEntity> {
        return continueWatchingDao.getContinueWatchingMovies(
            pageSize = pageSize,
            skip = (page - 1) * 20
        )
    }

    override suspend fun addContinueWatchedMovie(movieEntity: ContinueWatchingMovieEntity) {
        continueWatchingDao.addContinueWatchingMovie(movieEntity)

    }

    override suspend fun getContinueWatchedTVShow( pageSize: Int, page: Int): List<ContinueWatchingTVShowEntity> {
        return continueWatchingDao.getContinueWatchingTVShows(
            pageSize = pageSize,
            skip = (page - 1) * 20
        )
    }

    override suspend fun addContinueWatchedTVShow(tvShowEntity: ContinueWatchingTVShowEntity) {
        continueWatchingDao.addContinueWatchingTVShow(tvShowEntity)

    }

}