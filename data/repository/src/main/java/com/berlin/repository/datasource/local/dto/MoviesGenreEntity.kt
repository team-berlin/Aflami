package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_genre")
data class MoviesGenreEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val time: Long
)