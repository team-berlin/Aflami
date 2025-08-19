package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import com.berlin.repository.util.Constants.RECENT_SEARCH_HISTORY_TABLE
import com.berlin.repository.util.QueryType

@Entity(tableName = RECENT_SEARCH_HISTORY_TABLE, primaryKeys = ["query","type"])
data class RecentSearchHistoryEntity(
    val query: String,
    val type: QueryType,
    val time: Long
)
