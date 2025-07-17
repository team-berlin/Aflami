package com.berlin.aflami.screens.mediadetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.review.ReviewState

@Composable
fun ReviewSection(
    reviewState: ReviewState,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
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
                        isExpanded = isExpanded,
                        onToggleExpand = onToggleExpand
                    )
                }
            }
        }

        is ReviewState.NoReviewFound -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "There is no reviews!",
                    color = Theme.color.textColors.body,
                    style = Theme.textStyle.label.large,
                    textAlign = TextAlign.Center
                )
            }
        }

        is ReviewState.Reviewing.Error -> {
            ErrorMessage(Modifier, reviewState.errorMessage)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewSectionPreview() {
    AflamiTheme(isDarkTheme = false) {
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
}