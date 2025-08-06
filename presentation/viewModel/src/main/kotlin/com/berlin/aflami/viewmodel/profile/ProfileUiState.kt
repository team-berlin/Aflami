package com.berlin.aflami.viewmodel.profile


data class ProfileUiState(
    val coverImageUrl: String? = null,
    val userAvatarUrl: String? = null,
    val userName: String = "",
    val userPoints: Int = 0,
    val appVersion: String = "v1.0.0"
)
