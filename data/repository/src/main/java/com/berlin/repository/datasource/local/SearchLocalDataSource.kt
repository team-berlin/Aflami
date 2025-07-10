package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.model.SearchCaching

interface SearchLocalDataSource {
        suspend fun getCachedSearch(query: String): SearchCaching?
        suspend fun cacheSearch(entity: SearchCaching)
        suspend fun clearExpiredCache()

        suspend fun getSearchHistory(): List<SearchHistory>
        suspend fun insertSearchHistory(entity: SearchHistory)
        suspend fun clearSearchHistory()
    }

