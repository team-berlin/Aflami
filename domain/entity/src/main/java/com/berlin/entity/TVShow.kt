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
    val companyProductions: List<CompanyProduction>,
    val originCountry: String,
    val galleryUrl:List<String>,
    val numberOfSeasons:Int,
    val seasons: List<Season>,
    val reviews: List<Review>,
)
