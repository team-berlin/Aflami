package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_cache")
data class SearchingEntity(
    @PrimaryKey
    val id: Long,
    val query: String,
    val type: QueryType,
    val time: Long,
    val title: String,
    val rating: Double,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
    val page:Int,
)

enum class QueryType {
    ACTOR,
    COUNTRY,
    TV,
    MOVIE,
}