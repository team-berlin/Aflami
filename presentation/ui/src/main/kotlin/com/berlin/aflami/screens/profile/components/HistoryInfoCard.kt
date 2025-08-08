package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.berlin.aflami.ui.theme.Theme

@Composable
fun HistoryInfoCard(
    title: String,
    image: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.color.surfaceHigh)
                .border(1.dp, Theme.color.stroke, RoundedCornerShape(16.dp))
                .padding(end = 64.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.label.medium,
                color = Theme.color.textColors.title,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-8).dp)
                .size(width = 64.dp, height = 71.dp)
                .zIndex(1f),
            contentScale = ContentScale.Inside
        )
    }
}
