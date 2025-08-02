package com.berlin.aflami.viewmodel.shareduistate

data class ReviewUiState(
    val id: String = "",
    val name: String = "",
    val userName: String = "",
    val avatarImage: String?,
    val rating: Double = 0.0,
    val content: String = "",
    val date: String = "",
)