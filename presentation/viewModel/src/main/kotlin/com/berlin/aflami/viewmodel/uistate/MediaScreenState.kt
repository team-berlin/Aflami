package com.berlin.aflami.viewmodel.uistate

import androidx.compose.ui.graphics.painter.Painter

enum class MediaType { MOVIE, TV_SHOW }

data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<Int> = emptyList(),
    val poster: String = ""
)

data class MediaScreenState(
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
    val mediaType: MediaType,
    val rating: String = "",
    val backdrop: String? = "",
    val isFavorite: Boolean = false,
)

data class SeasonState(
    val id: Long = 0L,
    val numberOfEpisodes: Int = 0,
    val seasonTitle: String = "",
    val episodeTitle: String= "",
    val episodeDuration: Int = 0,
    val airDate: String = "",
    val episodeDescription: String = "",
    val poster: String = "",
    val rate: Float = 0f,
    val seasonNumber: Int = 0,
    val isPlaying: Boolean = false,
    val isExpanded: Boolean = false,
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