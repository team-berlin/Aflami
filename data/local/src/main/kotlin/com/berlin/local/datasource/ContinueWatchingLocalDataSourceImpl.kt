package com.berlin.local.datasource

import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity

class ContinueWatchingLocalDataSourceImpl(
    private val continueWatchingDao: ContinueWatchingDao
) : ContinueWatchingLocalDataSource {
    override suspend fun getContinueWatchingMovie(): List<ContinueWatchingMovieEntity> {
        return continueWatchingDao.getContinueWatchingMovies()
    }

    override suspend fun addContinueWatchedMovie(movieEntity: ContinueWatchingMovieEntity) {
        continueWatchingDao.addContinueWatchingMovie(movieEntity)

    }

    override suspend fun getContinueWatchedTVShow(): List<ContinueWatchingTVShowEntity> {
        return continueWatchingDao.getContinueWatchingTVShows()
    }

    override suspend fun addContinueWatchedTVShow(tvShowEntity: ContinueWatchingTVShowEntity) {
        continueWatchingDao.addContinueWatchingTVShow(tvShowEntity)

    }

}