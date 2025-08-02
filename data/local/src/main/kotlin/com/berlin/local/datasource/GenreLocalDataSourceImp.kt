package com.berlin.local.datasource

import com.berlin.local.dao.GenreDao
import com.berlin.repository.datasource.local.GenreLocalDataSource
import com.berlin.repository.datasource.local.dto.GenreEntity
import javax.inject.Inject

class GenreLocalDataSourceImpl  @Inject constructor (
    private val genreDao: GenreDao
) : GenreLocalDataSource {
    override suspend fun getCachedGenres(type: String): List<GenreEntity> {
        return genreDao.getCachedGenres(type)
    }

    override suspend fun cacheGenres(genres: List<GenreEntity>) {
        genreDao.cacheGenres(genres)
    }

    override suspend fun clearCachedGenres(type: String) {
        genreDao.clearCachedGenres(type)
    }
}