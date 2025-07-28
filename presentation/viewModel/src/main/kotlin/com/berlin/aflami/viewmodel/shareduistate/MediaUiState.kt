package com.berlin.aflami.viewmodel.shareduistate

enum class MediaType { MOVIE, TVSHOW }

data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<Int> = emptyList(),
    val poster: String = "",
    val mediaType:MediaType=MediaType.MOVIE
)
