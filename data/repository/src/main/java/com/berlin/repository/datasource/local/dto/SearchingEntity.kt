package com.berlin.repository.datasource.local.dto

import androidx.room.Entity

@Entity
    (tableName = "search_cache",
     primaryKeys = ["id"]
            )

data class SearchingEntity(
    val id: Long,
    val query: String,
    val type: String,
    val time: Long,
    val title: String,
    val rating: Double,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
)
