package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tVShow_genre")
data class TVShowGenreEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val time: Long
)