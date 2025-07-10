package com.berlin.local.repositoryImpl

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.model.SearchCaching

class SearchLocalDataSourceImpl(
    private val dao: SearchDao,
    ): SearchLocalDataSource {
    override suspend fun getCachedSearch(query: String, type: String): SearchCaching? {
        return dao.getCachedSearch(query,type)?.let{
            SearchCaching(
                query = it.query,
                time = it.time,
                type = it.type,
                id = it.id,
                title = it.title,
                rating = it.rating,
                releaseYear = it.releaseYear,
                description = it.description,
                genre = it.genre,
                poster = it.poster
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
