package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.QueryType
import com.berlin.repository.datasource.local.dto.SearchingEntity

interface SearchLocalDataSource {
    suspend fun getCachedSearch(
        query: String,
        type: QueryType,
        pageSize: Int = 20,
        page: Int
    ): List<SearchingEntity>
    suspend fun cacheSearch(movies: List<SearchingEntity>)
}