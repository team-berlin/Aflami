package com.berlin.aflami.viewmodel.uistate

import com.berlin.aflami.viewmodel.util.MediaType

data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val mediaType: MediaType = MediaType.MOVIE,
    val genre: List<Int> = emptyList(),
    val poster: String = ""
)
