package com.berlin.local.datasource

import com.berlin.local.dao.RecentHistoryDao
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.dto.SearchingEntity

class RecentHistoryLocalDataSourceImpl
    (private val recentHistoryDao: RecentHistoryDao)
    : RecentHistoryLocalDataSource {
    override suspend fun getRecentSearchQueries(): List<String> {
       return recentHistoryDao.getRecentSearchQueries()
    }

    override suspend fun insertQueryOnly(searchingEntity: SearchingEntity) {
        recentHistoryDao.insertQueryOnly(searchingEntity)
    }

    override suspend fun deleteQueryFromHistory(query: String) {
        recentHistoryDao.deleteQueryFromHistory(query)
    }

    override suspend fun clearSearchHistory() {
       recentHistoryDao.clearSearchHistory()
    }

}