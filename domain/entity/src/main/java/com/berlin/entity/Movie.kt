package com.berlin.entity


data class Movie(
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterURL: String,
    val screenShot: String,
    val description: String,
    val genres: List<Genre>,
    val duration: Int,
    val hasVideo: Boolean,
    val companyProductions: List<CompanyProduction>,
    val originCountry: String,
    val galleryUrl:List<String>,
    val reviews: List<Review>,
    val isFavourite:Boolean,
)
