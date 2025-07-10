package com.berlin.entity

import kotlinx.datetime.LocalDate

data class TvShow(
    val id :Long,
    val title:String,
    val rating:Double,
    val releaseYear: LocalDate,
    val description: String,
    val genre:List<Int>,
    val poster:String,
)
