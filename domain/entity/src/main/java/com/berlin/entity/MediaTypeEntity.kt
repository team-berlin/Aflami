package com.berlin.entity

import kotlinx.datetime.LocalDate

sealed class MediaTypeEntity {
    abstract val id: Long
    abstract val title: String
    abstract val rating: Double
    abstract val releaseYear: LocalDate
    abstract val description: String
    abstract val genre:List<Int>
    abstract val poster:String
}