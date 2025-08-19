package com.berlin.repository.datasource.local.datasource

import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import com.berlin.repository.util.Constants.DEFAULT_PAGE_SIZE

interface RecentlyWatchedLocalDataSource {
    suspend fun getRecentlyWatchedMovie(
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int
    ): List<RecentlyWatchedMovieEntity>

    suspend fun addRecentlyWatchedMovie(movieEntity: RecentlyWatchedMovieEntity)
    suspend fun getRecentlyWatchedTvShow(
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int
    ): List<RecentlyWatchedTvShowEntity>

    suspend fun addRecentlyWatchedTvShow(tvShowEntity: RecentlyWatchedTvShowEntity)

    suspend fun addCategoryAsPreference(categories: CategoriesPreferencesEntity)
    suspend fun getCategoryAsPreference(): List<CategoriesPreferencesEntity>

}