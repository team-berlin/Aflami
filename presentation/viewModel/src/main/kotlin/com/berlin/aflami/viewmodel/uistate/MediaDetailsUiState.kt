package com.berlin.aflami.viewmodel.uistate

import androidx.compose.ui.graphics.painter.Painter
import com.berlin.entity.Episodes

enum class MediaType { MOVIE, TV_SHOW }

data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val genre: List<Int> = emptyList(),
    val poster: String = "",
)

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
    val seasonsMap: MutableMap<Int, List<EpisodesUiState?>>? = mutableMapOf(),// remove this
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
    val numberOfSeasons: String? = null
)


data class EpisodesUiState(
    val stillPath: String,
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Int,
    val name: String,
    val overview: String,
    val runtime: Int,
    val voteAverage: Double
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