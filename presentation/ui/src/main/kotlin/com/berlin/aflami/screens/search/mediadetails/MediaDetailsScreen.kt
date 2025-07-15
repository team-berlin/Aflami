package com.berlin.aflami.screens.search.mediadetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.review.ReviewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    navController : NavController,
    viewModel: MediaDetailsViewmodel = koinViewModel()
) {
    val review by viewModel.reviewsUiState.collectAsState()

    // I know we need change this when edit navigation
    LaunchedEffect(Unit) {
        viewModel.getReviews(id = 79L, mediaType = MediaDetailsViewmodel.MediaType.SERIES)
    }

    MediaDetailsContent(
        reviewState = review,
        onToggleExpand = { viewModel.onReadMoreDescriptionClicked(id = 79L) },
        isExpanded = viewModel.isDescriptionExpanded(id = 79L)
    )

}

@Composable
fun MediaDetailsContent(
    reviewState: ReviewState,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {

    when (reviewState) {

        is ReviewState.Reviewing.Loading -> {
            Loading(Modifier)
        }

        is ReviewState.Reviewing.Success -> {
            val reviews = reviewState.data
            LazyColumn {
                itemsIndexed(reviews) { index, review ->
                    val isLast = index == reviews.lastIndex
                    ReviewItem(
                        review = review,
                        isLastItem = isLast,
                        isExpanded =isExpanded,
                        onToggleExpand = onToggleExpand
                    )
                }
            }
        }

        is ReviewState.NoReviewFound -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No Reviews Found",
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium,
                    textAlign = TextAlign.Center
                )
            }
        }

        is ReviewState.Reviewing.Error -> {
            ErrorMessage(Modifier, reviewState.errorMessage)
        }

    }
}

@Preview
@Composable
fun MediaDetailsContentPreview() {
    AflamiTheme {
        MediaDetailsContent(
            reviewState = TODO(),
            isExpanded = TODO(),
            onToggleExpand = TODO()
        )
    }
}