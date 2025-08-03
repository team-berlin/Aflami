package com.berlin.aflami.viewmodel.mediadetails.details.common

import androidx.compose.runtime.Immutable

@Immutable
data class ReviewUiState(
    val id: String = "",
    val name: String = "",
    val userName: String = "",
    val avatarImage: String?,
    val rating: Double = 0.0,
    val content: String = "",
    val date: String = "",
)

@Immutable
data class CompanyProductionUiState(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val country: String = "",
)