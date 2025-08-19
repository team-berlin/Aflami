package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.repository.util.Constants.TVSHOW_GENRE_TABLE

@Entity(tableName = TVSHOW_GENRE_TABLE)
data class TVShowGenreEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val time: Long
)