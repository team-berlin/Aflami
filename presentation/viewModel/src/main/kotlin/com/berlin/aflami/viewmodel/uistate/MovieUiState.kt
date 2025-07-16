package com.berlin.aflami.viewmodel.uistate

data class MovieUIState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<Int> = emptyList(),
    val poster: String = ""
)
