package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity

interface ContinueWatchingLocalDataSource {
    suspend fun getContinueWatchingMovie(): List<ContinueWatchingMovieEntity>

    suspend fun addContinueWatchedMovie(movieEntity: ContinueWatchingMovieEntity)
    suspend fun getContinueWatchedTVShow(): List<ContinueWatchingTVShowEntity>

    suspend fun addContinueWatchedTVShow(tvShowEntity: ContinueWatchingTVShowEntity)

}