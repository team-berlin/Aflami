package com.berlin.repository.datasource.local

import SearchCaching

interface SearchLocalDataSource {
        suspend fun getCachedSearch(query: String): SearchCaching?
        suspend fun cacheSearch(searchCaching: SearchCaching)
        suspend fun clearExpiredCache()
 }

