package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.SearchingEntity

interface SearchLocalDataSource {
    suspend fun getCachedSearch(query: String, type: String): List<SearchingEntity>
    suspend fun cacheSearch(movies: List<SearchingEntity>)

    suspend fun getRecentSearchQueries(): List<String>

    suspend fun insertQueryOnly(searchingEntity: SearchingEntity)

    suspend fun deleteQueryFromHistory(query: String)

    suspend fun clearSearchHistory()


}