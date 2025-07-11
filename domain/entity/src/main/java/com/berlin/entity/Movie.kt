package com.berlin.entity

import kotlinx.datetime.LocalDate

data class Movie(
    val id :Long,
    val title:String,
    val rating:Double,
    val releaseYear: LocalDate,
    val description: String,
    val genre:List<Int>,
    val poster:String,
)
//data class Movie (
//    override val id :Long,
//    override val title:String,
//    override val rating:Double,
//    override val releaseYear: LocalDate,
//    override val description: String,
//    override val genre:List<Int>,
//    override val poster:String,
//): MediaTypeEntity()