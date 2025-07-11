package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.SearchingEntity

interface SearchLocalDataSource {
    suspend fun getCachedSearch(query: String): List<SearchingEntity>
    suspend fun cacheSearch(movies: List<SearchingEntity>)
}