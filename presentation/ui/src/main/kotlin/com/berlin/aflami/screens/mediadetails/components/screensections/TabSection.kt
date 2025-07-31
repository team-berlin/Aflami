package com.berlin.aflami.screens.mediadetails.components.screensections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.Chips
import com.berlin.aflami.screens.mediadetails.components.CompanyProductionSection
import com.berlin.aflami.screens.mediadetails.components.GallerySection
import com.berlin.aflami.screens.mediadetails.components.MoreLikeThisSection
import com.berlin.aflami.screens.mediadetails.components.ReviewsSection
import com.berlin.aflami.screens.mediadetails.components.SeasonsSection
import com.berlin.aflami.screens.mediadetails.components.getMovieDetailsTabsIcon
import com.berlin.aflami.screens.mediadetails.components.movieDetailsTabsMapper
import com.berlin.aflami.screens.mediadetails.screen.getDisplayMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.TabContent
import com.berlin.aflami.viewmodel.shareduistate.MediaType

@Composable
fun TabSection(
    tabState: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
    rowState: RowSectionUiState,
    isReviewExpanded: (String) -> Boolean,
    onToggleReviewExpand: (String) -> Unit,
    mediaType: MediaType
) {
    val visibleTabs = MovieDetailsTabs.entries.filter {
        !(mediaType == MediaType.MOVIE && it == MovieDetailsTabs.SEASON)
    }

    LazyRow(
        modifier = Modifier
            .height(96.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(visibleTabs, key = { it.name }) { tab ->
            Chips(
                title = stringResource(movieDetailsTabsMapper(tab)),
                icon = painterResource(getMovieDetailsTabsIcon(tab)),
                isSelected = tab == tabState,
                onClick = { onChipClick(tab) }
            )
        }
    }

    when (val content = rowState) {
        is RowSectionUiState.Error,
        is RowSectionUiState.NoDataFound -> {
            Box(
                Modifier.padding(top = 32.dp, bottom = 82.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = content.getDisplayMessage(),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center
                )
            }
        }

        is RowSectionUiState.Loading -> Loading()

        is RowSectionUiState.Success -> when (val tab = content.content) {
            is TabContent.MoreLikeThis -> MoreLikeThisSection(mediaList = tab.items, mediaType = mediaType)
            is TabContent.Reviews -> ReviewsSection(reviews = tab.items,
                isExpanded = { id -> isReviewExpanded(id) },
                onToggleExpand = { id -> onToggleReviewExpand(id) })
            is TabContent.Gallery -> GallerySection(mediaImages = tab.items)
            is TabContent.CompanyProduction -> CompanyProductionSection(companyProductions = tab.items)
            is TabContent.Season -> SeasonsSection(seasonsMap = tab.items)
        }
    }
}