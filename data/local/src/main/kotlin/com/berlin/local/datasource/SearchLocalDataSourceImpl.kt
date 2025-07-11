package com.berlin.local.datasource

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.MovieEntity

class SearchLocalDataSourceImpl(
    private val searchDao: SearchDao
) : SearchLocalDataSource {
    override suspend fun getCachedSearch(query: String, type: String): List<MovieEntity> {
        return searchDao.getCachedSearch(query, type)
    }

    override suspend fun cacheSearch(searchCaching: List<MovieEntity>) {
        searchDao.cacheSearch(searchCaching)
    }
}