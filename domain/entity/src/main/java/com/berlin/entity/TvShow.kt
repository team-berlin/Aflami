package com.berlin.entity

data class TvShow(
    val id :Long,
    val title:String,
    val rating:Float,
    val releaseYear: String,
    val description: String,
    val genre:List<Int>,
    val poster:String,
)
