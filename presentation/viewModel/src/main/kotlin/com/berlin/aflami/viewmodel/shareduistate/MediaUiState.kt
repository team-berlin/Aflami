package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable
import com.berlin.entity.Genre

@Immutable
data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<Genre> = emptyList(),
    val poster: String = "",
    val mediaType:MediaType?,
)