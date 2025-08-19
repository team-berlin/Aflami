package com.berlin.repository.datasource.local.datasource

import com.berlin.repository.datasource.local.dto.RecentSearchHistoryEntity
import com.berlin.repository.util.Constants.DEFAULT_PAGE_SIZE
import com.berlin.repository.util.QueryType

interface SearchLocalDataSource {
    suspend fun getCachedSearch(
        query: String,
        type: QueryType,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int
    ): List<RecentSearchHistoryEntity>
    suspend fun cacheSearch(movies: List<RecentSearchHistoryEntity>)
}