package com.berlin.aflami.viewmodel.uistate

import androidx.compose.ui.graphics.painter.Painter
import com.berlin.aflami.viewmodel.util.MediaType

data class MediaDetailsUiState(
    val id: Long = 0L,
    val title: String = "",
    val overview: String = "",
    val posterUrl: String = "",
    val backdropUrl: String? = "",
    val genres: List<String> = emptyList(),
    val releaseYear: String = "",
    val rating: Double = 0.0,
    val runtime: String? = "",
    val seasons: List<EpisodesSeasonUiState>? = emptyList(),
    val numberOfSeasons: Int? = null,
    val isFavorite: Boolean = false,
    val isOverviewExpanded: Boolean = false,
    val mediaType: MediaType = MediaType.MOVIE,
    val isPlaying: Boolean = false,
    val mediaCast: List<MediaCastUiState> = emptyList(),
    val country: String = "",
    val options: List<MediaOptions> = emptyList(),
    val isLoading: Boolean = true,
    val originalCountry: String? = null,
    val duration: String? = null,
    val hasVideo: Boolean = false
)


data class EpisodesUiState(
    val stillPath: String,
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Int,
    val name: String,
    val overview: String,
    val runtime: String?,
    val voteAverage: Double,
)

data class EpisodesSeasonUiState(
    val idSeason: Int,
    val name: String,
    val episodes: List<EpisodesUiState?>,
    val seasonNumber: Int,
    val posterPath: String,
)

data class MediaOptions(
    val isSelected: Boolean,
    val title: String,
    val image: Painter,
)

data class MediaCastUiState(
    val mediaId: Long = 0L,
    val name: String = "",
    val poster: String = "",
)