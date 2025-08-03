package com.berlin.aflami.viewmodel.mediadetails.uistate

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter
import com.berlin.aflami.viewmodel.mediadetails.details.common.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.details.series.SeasonUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.Movie
import kotlinx.datetime.LocalDate

data class MediaDetailsScreenState(
    val mediaId: Long = 0L,
    val rating: Double = 0.0,
    val title: String = "",
    val overview: String = "",
    val posterUrl: String = "",
    val videoUrl:String?="",
//    val backdropUrl: String? = "",
    val genres: List<String> = emptyList(),
    val posterImages: List<String> = emptyList(),
    val backdropImages: List<String> = emptyList(),
    val releaseDate: String = "",
    val runtime: String? = "",
    val country: String = "",
    val description: String = "",
    val companyProductions: List<CompanyProductionUiState> = emptyList(),
    val actors: List<ActorUiState> = emptyList(),
    val seasons: List<SeasonUiState>? = emptyList(),
    val rowSection: MoviesRowSectionUiState = MoviesRowSectionUiState.Loading,
    val numberOfSeasons: Int? = null,
    val isFavorite: Boolean = false,
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
            companyProductions = emptyList(),
            originCountry = originalCountry,
            galleryUrl = ,
            reviews = TODO(),
        )
    }
}

//    fun toTVShow(): TVShow {
//        return TVShow(
//            id = mediaId,
//            title = title,
//            overview = description,
//            releaseYear = releaseDate.toLocalDate1(),
//            rating = rating,
//            runtime = 0,
//            genre = emptyList(),
//            poster = posterUrl,
//            backdropPath = backdropUrl,
//            releaseDate = releaseDate,
//        )
//    }
}

fun String.toLocalDate1(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}

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
}


sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(@StringRes val resId: Int) : UiText()
}