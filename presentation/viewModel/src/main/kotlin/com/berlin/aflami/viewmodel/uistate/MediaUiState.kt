package com.berlin.aflami.viewmodel.uistate

enum class MediaType { MOVIE, TV_SHOW }

data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val overview: String = "",
    val mediaType: MediaType,
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<String> = emptyList(),
    val poster: String = "",
    val backdrop: String? = "",
    val runtime: String = "",
    val country: String = "",
    val isFavorite: Boolean = false,
    val showReadMore: Boolean = false,
)