package com.berlin.local.repositoryImpl

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.model.SearchCaching

class SearchLocalDataSourceImpl(
    private val dao: SearchDao,
    ): SearchLocalDataSource {
    override suspend fun getCachedSearch(query: String): SearchCaching? {
        return dao.getCachedSearch(query)?.let{
            SearchCaching(
                query = it.query,
                history = it.history,
                time = it.time
            )
        }
    }

    override suspend fun cacheSearch(searchCaching: SearchCaching) {
        dao.cacheSearch(searchCaching)
    }
    override suspend fun clearExpiredCache() {
        val oneHourAgo = System.currentTimeMillis() - 60 * 60 * 1000
        dao.clearExpiredCache(oneHourAgo)
    }
}
