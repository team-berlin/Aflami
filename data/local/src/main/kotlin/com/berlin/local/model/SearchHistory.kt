package com.berlin.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory (
    @PrimaryKey val query: String,
    val time : Long,
)
