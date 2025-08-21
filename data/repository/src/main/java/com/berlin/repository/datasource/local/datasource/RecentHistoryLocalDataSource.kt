package com.berlin.repository.datasource.local.datasource

import com.berlin.repository.datasource.local.dto.RecentSearchHistoryEntity


interface RecentHistoryLocalDataSource {
    suspend fun getRecentSearchQueries(): List<String>

    suspend fun insertQueryOnly(searchingEntity: RecentSearchHistoryEntity)

    suspend fun deleteQueryFromHistory(query: String)

    suspend fun clearSearchHistory()
}