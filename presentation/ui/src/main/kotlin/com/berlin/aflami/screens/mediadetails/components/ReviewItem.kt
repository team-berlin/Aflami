package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.component.Rating
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.uistate.ReviewUiState
import com.berlin.designsystem.R

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: ReviewUiState,
    isLastItem: Boolean = false,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
            .padding(vertical = 12.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
        ) {
            AsyncImage(
                placeholder = painterResource(R.drawable.no_review_image),
                error = painterResource(R.drawable.no_review_image),
                fallback = painterResource(R.drawable.no_review_image),
                model = review.avatarImage,
                contentDescription = "Avatar Image",
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        BorderStroke(1.dp, Theme.color.stroke),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = review.name,
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium
                )
                Text(
                    text = "@${review.userName}",
                    color = Theme.color.textColors.hint,
                    style = Theme.textStyle.label.small
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Rating(
                modifier = Modifier
                    .offset(y = -(4).dp, x = 4.dp),
                review.rating.toString()
            )
        }

        ExpandableText(
            text = review.content,
            isExpanded = isExpanded,
            onToggleExpand = onToggleExpand,
            previewColor = Theme.color.textColors.hint,
            suffixColor = Theme.color.primary,
            previewStyle = Theme.textStyle.body.small,
            suffixStyle = Theme.textStyle.label.medium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
        )

        Text(
            review.date,
            color = Theme.color.textColors.hint,
            style = Theme.textStyle.label.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
        )

        if (!isLastItem) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Theme.color.stroke
            )
        }
    }
}

