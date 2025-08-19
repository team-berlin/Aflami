package com.berlin.local.datasource

import com.berlin.local.dao.GenrePreferencesDao
import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.repository.datasource.local.datasource.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import javax.inject.Inject

class RecentlyWatchedLocalDataSourceImpl @Inject constructor(
    private val continueWatchingDao: ContinueWatchingDao,
    private val genrePreferencesDao: GenrePreferencesDao
) : RecentlyWatchedLocalDataSource {
    override suspend fun getRecentlyWatchedMovie(
        pageSize: Int, page: Int
    ): List<RecentlyWatchedMovieEntity> {
        return continueWatchingDao.getContinueWatchingMovies(
            pageSize = pageSize, skip = (page - 1) * 20
        )
    }

    override suspend fun addRecentlyWatchedMovie(movieEntity: RecentlyWatchedMovieEntity) {
        movieEntity.genres.forEach {
            addCategoryAsPreference(
                CategoriesPreferencesEntity(
                    categoryId = it.toInt(),
                    count = genrePreferencesDao.getCount(it.toInt())?.plus(1) ?: 1
                )
            )
        }
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

    override suspend fun addCategoryAsPreference(categories: CategoriesPreferencesEntity) {
        genrePreferencesDao.insert(categories)
    }

    override suspend fun getCategoryAsPreference(): List<CategoriesPreferencesEntity> {
        return genrePreferencesDao.getAll()
    }
}