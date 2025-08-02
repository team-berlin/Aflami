package com.berlin.aflami.viewmodel.mediadetails.uistate

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.shareduistate.EpisodeUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.ReviewUiState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import kotlinx.datetime.LocalDate

data class MediaDetailsUiState(
    val mediaId: Long = 0L,
    val rating: Double = 0.0,
    val title: String = "",
    val overview: String = "",
    val posterUrl: String = "",
    val videoUrl:String?="",
    val backdropUrl: String? = "",
    val genres: List<String> = emptyList(),
    val posterImages: List<String> = emptyList(),
    val backdropImages: List<String> = emptyList(),
    val releaseDate: String = "",
    val runtime: String? = "",
    val country: String = "",
    val description: String = "",
    val actorUiStates: List<ActorUiState> = emptyList(),
    val seasons: List<EpisodesSeasonUiState>? = emptyList(),
    val rowSection: RowSectionUiState = RowSectionUiState.Loading,
    val numberOfSeasons: Int? = null,
    val isFavorite: Boolean = false,
    val isOverviewExpanded: Boolean = false,
    val mediaType: MediaType = MediaType.MOVIE,
    val isPlaying: Boolean = false,
    val options: List<MediaOptions> = emptyList(),
    val isDescriptionExpanded: Boolean = false,
    val expandedReviewIds: Set<String> = emptySet(),
    val isLoading: Boolean = true,
    val originalCountry: String? = null,
    val duration: String? = null,
    val hasVideo: Boolean = false,
    val error: String? = null,
) {
    fun toMovie(): Movie {
        return Movie(
            id = mediaId,
            title = title,
            description = description,
            releaseDate = releaseDate.toLocalDate1(),
            rating = rating,
            duration = 0,
            genres = emptyList(),
            posterURL = posterUrl,
            screenShot = backdropUrl,
            hasVideo = hasVideo,
            productionCompanies = emptyList(),
            originCountry = originalCountry,
            galleryUrl = ,
            reviews = TODO(),
        )
    }

    fun toTVShow(): TVShow {
        return TVShow(
            id = mediaId,
            title = title,
            overview = description,
            releaseYear = releaseDate.toLocalDate1(),
            rating = rating,
            runtime = 0,
            genre = emptyList(),
            poster = posterUrl,
            backdropPath = backdropUrl,
            releaseDate = releaseDate,
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
    val episodes: List<EpisodeUiState?>,
    val seasonNumber: Int,
    val posterPath: String,
)

data class MediaOptions(
    val isSelected: Boolean,
    val title: String,
    val image: Painter,
)

data class MovieDetailsTabsUiState(
    val tab: MovieDetailsTabs = MovieDetailsTabs.MORE_LIKE_THIS,
    val isSelected: Boolean = false,
)

enum class MovieDetailsTabs {
    MORE_LIKE_THIS,
    REVIEWS,
    GALLERY,
    COMPANY_PRODUCTION,
    SEASON
}

sealed class RowSectionUiState {
    object Loading : RowSectionUiState()
    data class Success(val content: TabContent) : RowSectionUiState()
    data class Error(val message: String? = null) : RowSectionUiState()
    data class NoDataFound(val message: UiText) : RowSectionUiState()
}

sealed class TabContent {
    data class MoreLikeThis(val items: List<MediaUiState>) : TabContent()
    data class Reviews(val items: List<ReviewUiState>) : TabContent()
    data class Gallery(val items: List<String>) : TabContent()
    data class Season(val items: MutableMap<Int, List<EpisodeUiState>>) : TabContent()
    data class CompanyProduction(val items: List<CompanyProductionUiState>) : TabContent()
}

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(@StringRes val resId: Int) : UiText()
}