package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity

interface RecentlyWatchedLocalDataSource {
    suspend fun getRecentlyWatchedMovie(
        pageSize: Int = 20,
        page: Int
    ): List<RecentlyWatchedMovieEntity>

    suspend fun addRecentlyWatchedMovie(movieEntity: RecentlyWatchedMovieEntity)
    suspend fun getRecentlyWatchedTvShow(
        pageSize: Int = 20,
        page: Int
    ): List<RecentlyWatchedTvShowEntity>

    suspend fun addRecentlyWatchedTvShow(tvShowEntity: RecentlyWatchedTvShowEntity)

    suspend fun addCategoryAsPreference(categories: CategoriesPreferencesEntity)
    suspend fun getCategoryAsPreference(): List<CategoriesPreferencesEntity>

}