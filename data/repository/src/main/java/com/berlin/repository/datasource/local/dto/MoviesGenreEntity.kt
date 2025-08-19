package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.repository.util.Constants.MOVIE_GENRE_TABLE

@Entity(tableName = MOVIE_GENRE_TABLE)
data class MoviesGenreEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val time: Long
)