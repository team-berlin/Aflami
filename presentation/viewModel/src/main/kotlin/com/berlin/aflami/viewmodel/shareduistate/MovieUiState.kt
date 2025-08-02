package com.berlin.aflami.viewmodel.shareduistate

import com.berlin.entity.Genre

data class MovieUIState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<Genre> = emptyList(),
    val posterUrl: String = "",
)
