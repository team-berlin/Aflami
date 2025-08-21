package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import com.berlin.repository.util.Constants.HOME_MOVIE_TABLE

@Entity(tableName = HOME_MOVIE_TABLE, primaryKeys = ["id", "homeSection"])
data class HomeMovieEntity(
    val id: Long,
    val homeSection: HomeSection,
    val title: String,
    val rating: String,
    val releaseYear: String,
    val genre: List<Int>,
    val poster: String,
)