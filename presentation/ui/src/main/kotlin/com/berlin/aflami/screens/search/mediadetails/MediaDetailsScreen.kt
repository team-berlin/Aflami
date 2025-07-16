package com.berlin.aflami.screens.search.mediadetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.component.Chips
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.review.ReviewState
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    navController: NavController,
    viewModel: MediaDetailsViewmodel = koinViewModel()
) {
    val review by viewModel.reviewsUiState.collectAsState()
    val tabSelected by viewModel.tabSelectedUiState.collectAsState()


    MediaDetailsContent(
        reviewState = review,
        onToggleExpand = { viewModel.onReadMoreDescriptionClicked(id = TODO()) },
        isExpanded = viewModel.isDescriptionExpanded(id = TODO()),
        isSelectedTab = tabSelected.tab,
        onChipClick = { tab ->
            viewModel.toggleMovieDetailsTab(
                tab = tab,
                mediaId = TODO(),
                mediaType = TODO()
            )
        }
    )

}

@Composable
fun MediaDetailsContent(
    reviewState: ReviewState,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    isSelectedTab: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .height(96.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(MovieDetailsTabs.entries) { tab ->
            Chips(
                title = stringResource(movieDetailsTabsMapper(tab)),
                icon = painterResource(getMovieDetailsTabsIcon(tab)),
                isSelected = tab == isSelectedTab,
                onClick = { onChipClick(tab) }
            )
        }
    }

    if (isSelectedTab == MovieDetailsTabs.REVIEWS) {
        ReviewSection(
            reviewState = reviewState,
            isExpanded = isExpanded,
            onToggleExpand = onToggleExpand
        )
    }
}

@Preview
@Composable
fun MediaDetailsContentPreview() {
    AflamiTheme {
        MediaDetailsContent(
            reviewState = TODO(),
            isExpanded = TODO(),
            onToggleExpand = TODO(),
            isSelectedTab = TODO(),
            onChipClick = TODO()
        )
    }
}

private fun movieDetailsTabsMapper(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.string.more_like_this
        MovieDetailsTabs.REVIEWS -> R.string.reviews
        MovieDetailsTabs.GALLERY -> R.string.gallery
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.string.company_production
    }
}

private fun getMovieDetailsTabsIcon(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> com.berlin.ui.R.drawable.camera_video
        MovieDetailsTabs.REVIEWS -> R.drawable.star
        MovieDetailsTabs.GALLERY -> com.berlin.ui.R.drawable.album
        MovieDetailsTabs.COMPANY_PRODUCTION -> com.berlin.ui.R.drawable.city
    }
}
