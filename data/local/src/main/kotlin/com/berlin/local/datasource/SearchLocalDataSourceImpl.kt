package com.berlin.local.datasource

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.SearchingEntity
import kotlinx.coroutines.flow.Flow

class SearchLocalDataSourceImpl(
    private val searchDao: SearchDao
) : SearchLocalDataSource {
    override suspend fun getCachedSearch(query: String, type: String): List<SearchingEntity> {
        return searchDao.getCachedSearch(query, type)
    }
    override suspend fun cacheSearch(movies: List<SearchingEntity>) {
        searchDao.cacheSearch(movies)
    }

    override suspend fun getRecentSearchQueries(): List<String> {
        return searchDao.getRecentSearchQueries()
    }
    override suspend fun insertQueryOnly(searchingEntity: SearchingEntity) {
        searchDao.insertQueryOnly(searchingEntity)
    }
}