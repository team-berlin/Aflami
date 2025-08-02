package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable

@Immutable
data class CompanyProductionUiState(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val country: String = "",
)