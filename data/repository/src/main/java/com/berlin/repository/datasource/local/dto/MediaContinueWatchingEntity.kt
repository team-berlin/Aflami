package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie_Continue_Watching")
data class ContinueWatchingMovieEntity(
    @PrimaryKey
    val id:Long,
    val title: String,
    val rating: Double,
    val posterUrl:String,
    val typeOfMedia:String,
    val releaseYear: String,
)

@Entity(tableName = "TVShow_Continue_Watching")
data class ContinueWatchingTVShowEntity(
    @PrimaryKey
    val id:Long,
    val title: String,
    val rating: Double,
    val posterUrl:String,
    val typeOfMedia:String,
    val releaseYear: String,
)


