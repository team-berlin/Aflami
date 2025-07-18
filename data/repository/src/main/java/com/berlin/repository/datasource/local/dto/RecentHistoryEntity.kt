package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import com.berlin.repository.util.QueryType

@Entity(tableName = "search_cache", primaryKeys = ["query","type"])
data class RecentHistoryEntity(
    val query: String,
    val type: QueryType,
    val time: Long
)
