package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie_Continue_Watching")
data class RecentlyWatchedMovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterURL: String,
    val screenShot: String,
    val description: String,
    val genres: List<String>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<String>,
    val originCountry: String,
    val galleryUrl: List<String>,
    val reviews: List<String>,
)

@Entity(tableName = "TVShow_Continue_Watching")
data class RecentlyWatchedTvShowEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val rating: Double,
    val posterURL: String,
    val releaseDate: String,
    val screenShot: String,
    val description: String,
    val genres: List<String>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<String>,
    val originCountry: String,
    val seasons: List<String>,
    val galleryUrl: List<String>,
    val reviews: List<String>,
)


