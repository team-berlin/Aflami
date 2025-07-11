package com.berlin.repository.datasource.local.dto

import androidx.room.Entity

@Entity(
    tableName = "search_cache",
    primaryKeys = ["query", "id"]
)
data class MovieEntity(
    val query: String,
    val id: Long,
    val type: String,
    val time: Long,
    val title: String,
    val rating: Double,
    val releaseYear: String,
    val description: String,
    val genre: List<Int>,
    val poster: String,
)
