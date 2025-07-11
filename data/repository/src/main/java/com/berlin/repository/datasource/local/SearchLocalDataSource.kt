package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MovieEntity
import com.berlin.repository.datasource.local.model.SearchCaching

interface SearchLocalDataSource {
    suspend fun getCachedSearch(query: String): List<MovieEntity>
    suspend fun cacheSearch(searchCaching: List<MovieEntity>)
    suspend fun clearExpiredCache()
 }

