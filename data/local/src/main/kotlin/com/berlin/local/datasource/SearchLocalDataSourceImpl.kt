package com.berlin.local.datasource

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.model.SearchCaching

class SearchLocalDataSourceImpl(
    private val dao: SearchDao,
): SearchLocalDataSource {
    override suspend fun getCachedSearch(query: String, type: String): List<SearchCaching> {
        return dao.getCachedSearch(query,type)
    }

    override suspend fun cacheSearch(searchCaching: List<SearchCaching>) {
        dao.cacheSearch(searchCaching)
    }
    override suspend fun clearExpiredCache() {
        val oneHourAgo = System.currentTimeMillis() - 60 * 60 * 1000
        dao.clearExpiredCache(oneHourAgo)
    }
}
