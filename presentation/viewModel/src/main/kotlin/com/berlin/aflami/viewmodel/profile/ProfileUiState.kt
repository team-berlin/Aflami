package com.berlin.aflami.viewmodel.profile

import androidx.compose.ui.graphics.painter.Painter


data class ProfileUiState(
    val coverImage: Painter,
    val userAvatar: Painter,
    val userName: String,
    val userPoints: Int,
    val appVersion: String
)