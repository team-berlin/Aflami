package com.berlin.local.datasource

import com.berlin.local.dao.SearchDao
import com.berlin.local.dao.SearchHistoryDao
import com.berlin.repository.datasource.local.SearchHistoryDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.local.dto.SearchingHistory
import kotlinx.coroutines.flow.Flow

class SearchHistoryDataSourceImpl(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryDataSource {
    override fun getSearchHistory(): Flow<List<SearchingHistory>> {
        return searchHistoryDao.getAllSearches()
    }

    override suspend fun addSearch(query: String) {
        searchHistoryDao.insertSearch(SearchingHistory(query = query))
    }

    override suspend fun deleteById(id: Int) {
        searchHistoryDao.deleteSearchById(id)
    }

    override suspend fun clearAll() {
        searchHistoryDao.clearAll()
    }

}