package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    mediaImages: List<String>,
    cellWidth: Dp = 160.dp,
    cellHeight: Dp = 145.dp,
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 8.dp,
    sidePadding: Dp = 16.dp
) {

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        val backDropsList= mediaImages.take(10)
        val maxGridWidth = this.maxWidth - 2 * sidePadding
        val columns = (maxGridWidth / (cellWidth + horizontalSpacing)).toInt().coerceAtLeast(2)

        val adjustedSpacing = horizontalSpacing * (columns - 1)
        val adjustedCellWidth = (maxGridWidth - adjustedSpacing) / columns

        val rows = (backDropsList.size + columns - 1) / columns

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = sidePadding, end = sidePadding, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        ) {
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
                ) {
                    for (col in 0 until columns) {
                        val index = row * columns + col
                        if (index < backDropsList.size) {
                            AsyncImage(
                                model = backDropsList[index],
                                contentDescription = stringResource(com.berlin.ui.R.string.cast),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(adjustedCellWidth)
                                    .height(cellHeight)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, Theme.color.stroke),
                                placeholder = painterResource(R.drawable.place_holder),
                                error = painterResource(R.drawable.place_holder),
                                fallback = painterResource(R.drawable.place_holder),
                            )
                        } else {
                            // Add a spacer to balance the row visually
                            Spacer(modifier = Modifier.width(adjustedCellWidth))
                        }
                    }
                }
            }
        }
    }
}