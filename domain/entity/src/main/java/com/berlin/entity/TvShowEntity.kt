package com.berlin.entity

import kotlinx.datetime.LocalDate

data class TvShowEntity(
    override val id :Long,
    override val title:String,
    override val rating: Double,
    override val releaseYear: LocalDate,
    override val description: String,
    override val genre:List<Int>,
    override val poster:String,
): MediaTypeEntity()
