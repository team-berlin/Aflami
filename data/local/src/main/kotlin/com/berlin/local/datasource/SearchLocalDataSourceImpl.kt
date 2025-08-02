package com.berlin.local.datasource

import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity
import javax.inject.Inject

class SearchLocalDataSourceImpl  @Inject constructor (
    private val searchDao: SearchDao
) : SearchLocalDataSource {
    override suspend fun getCachedSearch(
        query: String,
        type: QueryType,
        pageSize: Int,
        page: Int
    ): List<SearchingEntity> {
        return searchDao.getCachedSearch(
            query = query,
            type = type,
            pageSize = pageSize,
            skip = (page - 1) * 20
        )
    }
    override suspend fun cacheSearch(movies: List<SearchingEntity>) {
        searchDao.cacheSearch(movies)
    }
}