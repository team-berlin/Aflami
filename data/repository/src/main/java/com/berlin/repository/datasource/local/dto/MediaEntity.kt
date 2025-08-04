package com.berlin.repository.datasource.local.dto


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie_Home")
data class MovieHomeEntity(
    @PrimaryKey
    val id: Long,
    val title: String ,
    val rating: String,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "TVShow_Home")
data class TVShowHomeEntity(
    @PrimaryKey
    val id: Long,
    val title: String ,
    val rating: String,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
    val addedAt: Long = System.currentTimeMillis()
)