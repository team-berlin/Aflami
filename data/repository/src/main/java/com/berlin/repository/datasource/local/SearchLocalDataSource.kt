package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.model.SearchCaching

interface SearchLocalDataSource {
        suspend fun getCachedSearch(query: String, type: String): SearchCaching?
        suspend fun cacheSearch(searchCaching: SearchCaching)
        suspend fun clearExpiredCache()
 }

