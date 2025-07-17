package com.berlin.aflami.viewmodel.uistate

data class EpisodeUi(
    val id: Int,
    val episodeNumber: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val time: String,
    val date: String,
    val rating: String,
)
data class SeasonUi(
    val seasonNumber: String,
    val episodes: List<EpisodeUi>
)
