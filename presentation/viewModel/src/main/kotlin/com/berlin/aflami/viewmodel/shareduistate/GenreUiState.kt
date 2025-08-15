package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable
import com.berlin.entity.Genre

@Immutable
data class GenreUiState(
    val id: Int=0,
    val name: String="",
)
fun Genre.toGenreUiState() = GenreUiState(id, name)
fun GenreUiState.toDomain() = Genre(
    id = id ,
    name = name
)