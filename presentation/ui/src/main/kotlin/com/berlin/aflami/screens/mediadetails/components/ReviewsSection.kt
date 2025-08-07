package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.berlin.aflami.viewmodel.details.common.ReviewUiState

@Composable
fun ReviewsSection(
    reviews: List<ReviewUiState>,
    isExpanded: (String) -> Boolean,
    onToggleExpand: (String) -> Unit
) {
    Column {
        reviews.forEachIndexed { index, review ->
            val isLast = index == reviews.lastIndex
            ReviewItem(
                review = review,
                isLastItem = isLast,
                isExpanded = isExpanded(review.id),
                onToggleExpand = { onToggleExpand(review.id) }
            )
        }
    }
}