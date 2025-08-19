package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.repository.util.Constants.MOVIE_CONTINUE_WATCHING_TABLE
import com.berlin.repository.util.Constants.TVSHOW_CONTINUE_WATCHING_TABLE

@Entity(tableName = MOVIE_CONTINUE_WATCHING_TABLE)
data class RecentlyWatchedMovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterURL: String,
    val screenShot: String,
    val description: String,
    val genres: List<Int>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<String>,
    val originCountry: String,
    val galleryUrl: List<String>,
    val reviews: List<String>,
)

@Entity(tableName = TVSHOW_CONTINUE_WATCHING_TABLE)
data class RecentlyWatchedTvShowEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val rating: Double,
    val posterURL: String,
    val releaseDate: String,
    val screenShot: String,
    val description: String,
    val genres: List<Int>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<String>,
    val originCountry: String,
    val seasons: List<String>,
    val galleryUrl: List<String>,
    val reviews: List<String>,
)


