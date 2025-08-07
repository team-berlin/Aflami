package com.berlin.entity


data class Review(
    val id: String,
    val name: String,
    val userName: String,
    val avatarImage: String,
    val rating: Double,
    val content: String,
    val date: String,
)
