package com.berlin.entity

import kotlinx.datetime.LocalDate

data class Review(
    val id: Long,
    val name: String,
    val userName: String,
    val avatarImage: String,
    val rating: Double,
    val content: String,
    val date: LocalDate,
)
