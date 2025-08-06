package com.berlin.repository.datasource.local.dto

import androidx.room.Entity

@Entity(tableName = "Movie_Home", primaryKeys = ["id", "sectionHome"])
data class MovieHomeEntity(
    val id: Long,
    val sectionHome: SectionHome,
    val title: String,
    val rating: String,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "TVShow_Home", primaryKeys = ["id", "sectionHome"])
data class TVShowHomeEntity(
    val id: Long,
    val sectionHome: SectionHome,
    val title: String,
    val rating: String,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
    val addedAt: Long = System.currentTimeMillis()
)
enum class SectionHome {
    POPULAR,
    TOP_RATING,
    UPCOMING
}