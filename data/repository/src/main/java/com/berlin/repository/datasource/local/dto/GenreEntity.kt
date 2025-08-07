package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "genre_cache")
data class GenreEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val type: String,
    val time: Long
)