package com.berlin.aflami.viewmodel.review

data class ReviewUiState(
    val id: Long = 0L,
    val name: String = "",
    val userName: String = "",
    val avatarImage: String = "",
    val rating: Double = 0.0,
    val content: String = "",
    val date: String = "",
)