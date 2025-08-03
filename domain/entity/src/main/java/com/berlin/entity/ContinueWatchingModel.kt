package com.berlin.entity

import kotlinx.datetime.LocalDate

data class ContinueWatchingModel(
    val id: Long,
    val rating: String,
    val title: String,
    val releaseDate: String,
    val posterUrl: String,
)
