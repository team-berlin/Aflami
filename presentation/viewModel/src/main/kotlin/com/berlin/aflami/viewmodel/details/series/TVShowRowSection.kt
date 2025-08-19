package com.berlin.aflami.viewmodel.details.series

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.details.movie.UiText
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState

sealed interface TVShowRowSectionUiState {
    object Loading : TVShowRowSectionUiState
    data class Success(val content: TVShowTabContent) : TVShowRowSectionUiState
    data class Error(val message: String? = null) : TVShowRowSectionUiState
    data class NoDataFound(val message: UiText) : TVShowRowSectionUiState
}

sealed interface TVShowTabContent {
    data class MoreLikeThis(val items: List<TVShowUiState>) : TVShowTabContent
    data class Reviews(val reviews: List<ReviewUiState>) : TVShowTabContent
    data class Gallery(val images: List<String>) : TVShowTabContent
    data class Season(val seasonToEpisodesMap: MutableMap<Int, List<EpisodeUiState>>) : TVShowTabContent
    data class CompanyProduction(val companyProductionStates: List<CompanyProductionUiState>) : TVShowTabContent
}


@Immutable
data class EpisodeUiState(
    val stillPath: String,
    val airDate: String?=null,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Long,
    val name: String,
    val overview: String,
    val runtime: String?,
    val voteAverage: Double,
)