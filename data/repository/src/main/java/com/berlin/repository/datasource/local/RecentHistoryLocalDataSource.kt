package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.SearchingEntity

interface RecentHistoryLocalDataSource {
    suspend fun getRecentSearchQueries(): List<String>

    suspend fun insertQueryOnly(searchingEntity: SearchingEntity)

    suspend fun deleteQueryFromHistory(query: String)

    suspend fun clearSearchHistory()
}