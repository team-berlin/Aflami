package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Media_Continue_Watching")
data class MediaContinueWatchingEntity(
    @PrimaryKey
    val id:Long,
    val title: String,
    val rating: Double,
    val posterUrl:String,
    val typeOfMedia:String,
    val releaseYear: String,
)