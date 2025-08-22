package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.screens.mediadetails.components.CompanyProductionSection
import com.berlin.aflami.screens.mediadetails.components.GallerySection
import com.berlin.aflami.screens.mediadetails.components.ReviewsSection
import com.berlin.aflami.screens.mediadetails.components.TVShowRowSection
import com.berlin.aflami.screens.mediadetails.components.TvShowMoreLikeThisSection
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsUiState
import com.berlin.aflami.viewmodel.details.series.TVShowRowSectionUiState
import com.berlin.aflami.viewmodel.details.series.TVShowTabContent


fun LazyListScope.tvShowRowSectionUiStateError(
    state: TVShowRowSectionUiState,
    listener: MediaDetailsScreenInteractionListener
) {
    item {
        TVShowRowSection(
            state = state,
            listener = listener
        ) { tvShowRowSectionUiState, listener ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
            {
                if (tvShowRowSectionUiState is TVShowRowSectionUiState.Error
                    || tvShowRowSectionUiState is TVShowRowSectionUiState.NoDataFound
                ) {
                    Box(
                        Modifier.padding(top = 32.dp, bottom = 82.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = tvShowRowSectionUiState.getDisplayMessage(),
                            style = Theme.textStyle.label.large,
                            color = Theme.color.textColors.body,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.tvShowRowSectionUiStateLoading(
    state: TVShowRowSectionUiState,
    listener: MediaDetailsScreenInteractionListener
) {
    item {
        TVShowRowSection(
            state = state,
            listener = listener
        ) { tvShowRowSectionUiState, listener ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
            {
                if (tvShowRowSectionUiState is TVShowRowSectionUiState.Loading) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.moreLikeThisSection(
    state: TVShowRowSectionUiState,
    listener: MediaDetailsScreenInteractionListener
) {
    item {
        TVShowRowSection(
            state = state,
            listener = listener
        )
        { tvShowRowSectionUiState, listener ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
            {
                if (tvShowRowSectionUiState is TVShowRowSectionUiState.Success) {
                    val tab = tvShowRowSectionUiState.content
                    if (tab is TVShowTabContent.MoreLikeThis) {
                        TvShowMoreLikeThisSection(
                            mediaList = tab.items,
                            onMediaClick = { mediaId ->
                                listener.onMediaCardClicked(mediaId)
                            },
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.reviewSection(
    state: TVShowRowSectionUiState,
    tvShowDetailsUiState: TVShowDetailsUiState,
    listener: MediaDetailsScreenInteractionListener
) {
    item {
        TVShowRowSection(
            state = state,
            listener = listener
        ) { tvShowRowSectionUiState, listener ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
            {
                if (tvShowRowSectionUiState is TVShowRowSectionUiState.Success) {
                    val tab = tvShowRowSectionUiState.content
                    if (tab is TVShowTabContent.Reviews) {
                        ReviewsSection(
                            reviews = tab.reviews,
                            isExpanded = { id -> tvShowDetailsUiState.expandedReviewIds.contains(id) },
                            onToggleExpand = { id ->
                                listener.onReadMoreReviewClicked(
                                    id
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.gallerySection(
    state: TVShowRowSectionUiState,
    listener: MediaDetailsScreenInteractionListener
) {
    item {
        TVShowRowSection(
            state = state,
            listener = listener
        ) { tvShowRowSectionUiState, listener ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
            {
                if (tvShowRowSectionUiState is TVShowRowSectionUiState.Success) {
                    val tab = tvShowRowSectionUiState.content
                    if (tab is TVShowTabContent.Gallery) {
                        GallerySection(mediaImages = tab.images)
                    }
                }
            }
        }
    }
}

fun LazyListScope.companyProductionSection(
    state: TVShowRowSectionUiState,
    listener: MediaDetailsScreenInteractionListener
) {
    item {

        TVShowRowSection(
            state = state,
            listener = listener
        ) { tvShowRowSectionUiState, listener ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
            {
                if (tvShowRowSectionUiState is TVShowRowSectionUiState.Success) {
                    val tab = tvShowRowSectionUiState.content
                    if (tab is TVShowTabContent.CompanyProduction) {
                        CompanyProductionSection(
                            companyProductions = tab.companyProductionStates
                        )
                    }
                }
            }
        }
    }
}