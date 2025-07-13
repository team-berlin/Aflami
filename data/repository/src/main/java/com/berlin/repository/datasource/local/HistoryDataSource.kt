package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.local.dto.SearchingHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryDataSource {
        fun getSearchHistory(): Flow<List<SearchingHistory>>
        suspend fun addSearch(query: String)
        suspend fun deleteById(id: Int)
        suspend fun clearAll()
    }

