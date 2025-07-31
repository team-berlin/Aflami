package com.berlin.aflami.viewmodel.mediadetails.uistate

import androidx.compose.ui.graphics.painter.Painter
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import kotlinx.datetime.LocalDate

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
    val isDescriptionExpanded: Boolean = false,
    val expandedReviewIds: Set<String> = emptySet(),
    val isLoading: Boolean = true,
    val originalCountry: String? = null,
    val duration: String? = null,
    val hasVideo: Boolean = false,
    val error: String? = null,
    val rowSection: RowSectionUiState = RowSectionUiState.Loading
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            description = overview,
            releaseDate = releaseDate.toLocalDate1(),
            rating = rating,
            duration = 0,
            genres = emptyList(),
            poster = posterUrl,
            screenShot = backdropUrl,
            releaseDate = releaseYear,
        )
    }

    fun toTVShow(): TVShow {
        return TVShow(
            id = id,
            title = title,
            overview = overview,
            releaseYear = releaseYear.toLocalDate1(),
            rating = rating,
            runtime = 0,
            genre = emptyList(),
            poster = posterUrl,
            backdropPath = backdropUrl,
            releaseDate = releaseYear,
        )
    }
}

fun String.toLocalDate1(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}

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

data class ReviewUiState(
    val id: String = "",
    val name: String = "",
    val userName: String = "",
    val avatarImage: String?,
    val rating: Double = 0.0,
    val content: String = "",
    val date: String = "",
)

data class CompanyProductionUiState(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val country: String = "",
)