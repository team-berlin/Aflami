package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.berlin.aflami.viewmodel.uistate.ReviewUiState

@Composable
fun ReviewsSection(
    reviews: List<ReviewUiState>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Column {
        reviews.forEachIndexed { index, review ->
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