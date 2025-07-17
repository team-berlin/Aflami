package com.berlin.aflami.viewmodel.uistate

import androidx.compose.ui.graphics.painter.Painter

enum class MediaType { MOVIE, TV_SHOW }

data class MediaDetailsScreenUiState(
    val id: Long = 0L,
    val poster: String = "",
    val isPlaying: Boolean = false,
    val title: String = "",
    val genre: List<Int> = emptyList(),
    val releaseYear: String = "",
    val mediaDuration: String = "",
    val overview: String = "",
    val isOverviewExpanded: Boolean = false,
    val mediaCast: List<MediaCastUiState> = emptyList(),
    val country: String = "",
    val options: List<MediaOptions> = emptyList(),
    val mediaType: MediaType=MediaType.MOVIE,
    val rating: String = "",
    val backdrop: String? = "",
    val isFavorite: Boolean = false,
)

data class EpisodeUi(
    val id: Int,
    val episodeNumber: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val time: String,
    val date: String,
    val rating: String,
    val isPlaying: Boolean = false,
    val isExpanded: Boolean = false,
)
data class SeasonUiState(
    val seasonNumber: String,
    val episodes: List<EpisodeUi>
)

data class MediaOptions(
    val isSelected: Boolean,
    val title: String,
    val image: Painter
)

data class MediaCastUiState(
    val mediaId:Long =0L,
    val name:String="",
    val poster:String=""
)