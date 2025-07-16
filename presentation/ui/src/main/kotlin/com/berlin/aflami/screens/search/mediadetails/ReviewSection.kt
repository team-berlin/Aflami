package com.berlin.aflami.screens.search.mediadetails

import android.graphics.fonts.FontStyle
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.Rating
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.ExpandableText
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.textstyle.IBM
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.review.ReviewState
import com.berlin.ui.R
import java.time.format.TextStyle

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