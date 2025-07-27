package com.berlin.entity

data class TVShow(
    val id :Long,
    val title:String,
    val rating: Double,
    val posterURL:String,
    val releaseDate: String,
    val screenShot: String,
    val description: String,
    val genres: List<Genre>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<ProductionCompany>,
    val originCountry: String,
    val seasons: List<Season>,
    val galleryUrl:List<String>,
    val reviews: List<Review>,
)
