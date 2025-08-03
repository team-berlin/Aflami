package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable
import com.berlin.entity.Genre

@Immutable
data class GenreUiState(
    val id: Int? = null,
    val name: String? = null,
)
fun Genre.toGenreUiState() = GenreUiState(id, name)