package com.berlin.repository.datasource.local.dto

import androidx.room.PrimaryKey
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Review
import com.berlin.entity.Season

data class TVShowEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val rating: Double,
    val posterURL: String,
    val releaseDate: String,
    val screenShot: String,
    val description: String,
    val genres: List<Genre>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<CompanyProduction>,
    val originCountry: String,
    val seasons: List<Season>,
    val galleryUrl: List<String>,
    val reviews: List<Review>,
)

