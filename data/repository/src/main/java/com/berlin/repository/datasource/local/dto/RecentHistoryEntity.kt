package com.berlin.repository.datasource.local.dto

import androidx.room.Entity

@Entity(tableName = "recent_history", primaryKeys = ["query","type"])
data class RecentHistoryEntity(
    val query: String,
    val type: QueryType,
    val time: Long
)
