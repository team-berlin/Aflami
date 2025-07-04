package com.berlin.aflami.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.modifier.dropShadow
import com.berlin.aflami.ui.theme.Theme

@Composable
fun SearchSuggestionItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    contentDescription: String,
    gradientBackground: Brush,
    painter: Painter,
    size: Dp = 56.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(gradientBackground),

        ) {
        Box(
            modifier = Modifier
                .size(size)
                .align(Alignment.TopEnd)
                .dropShadow(
                    blur = 32.dp,
                    color = Theme.color.textColors.onPrimary.copy(0.12f),
                    shape = CircleShape
                )
                .offset(x = 5.dp, y = -(5).dp)
        )
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .height(40.dp),
                contentScale = ContentScale.FillHeight
            )
            Text(
                title,
                color = Theme.color.textColors.onPrimary,
                style = Theme.textStyle.title.small,
            )
            Text(
                subtitle,
                color = Theme.color.textColors.onPrimaryBody,
                style = Theme.textStyle.label.small,
            )
        }
    }
}