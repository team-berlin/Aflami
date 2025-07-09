package com.berlin.aflami.screens.search.uistate

data class MovieUIState(
    val id: Long=0L,
    val title: String="",
    val rating: Float=0.0f,
    val releaseYear: String="",
    val description: String="",
    val genre: List<String> = emptyList(),
    val poster: String =""
)
