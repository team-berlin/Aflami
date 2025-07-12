package com.berlin.local.datasource

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.SearchingEntity

class SearchLocalDataSourceImpl(
    private val searchDao: SearchDao
) : SearchLocalDataSource {
    override suspend fun getCachedSearch(query: String,type:String): List<SearchingEntity> {
        return searchDao.getCachedSearch(query,type)
    }

    override suspend fun cacheSearch(movies: List<SearchingEntity>) {
        searchDao.cacheSearch(movies)
    }
}