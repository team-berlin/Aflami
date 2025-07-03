package com.berlin.aflami.component

import android.graphics.BlurMaskFilter
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SearchSuggestionItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    backgroundColor: List<Color>,
    painter: Painter,
) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    backgroundColor,
                    end = Offset(0f, Float.POSITIVE_INFINITY),
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .height(98.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopEnd)
                .dropShadow(blur = 32.dp, color = Theme.color.textColors.onPrimary.copy(0.12f))
                .offset(x = 5.dp, y = -(5).dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 4.dp, top = 10.dp, end = 8.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "search suggest photo",
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .size(height = 40.dp, width = 49.dp)
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

@Composable
fun SearchSuggestionHub() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SearchSuggestionItem(
            modifier = Modifier.weight(1f),
            title = "World Tour",
            subtitle = "Explore World Cinema",
            backgroundColor = listOf(
                Theme.color.primary,
                Color(0xFF803559)
            ),
            painter = painterResource(R.drawable.news_img)
        )
        SearchSuggestionItem(
            modifier = Modifier.weight(1f),
            title = "Find by Actor",
            subtitle = "Search by favorite Actor",
            backgroundColor =
                listOf(
                    Color(0xFF53ABF9),
                    Color(0xFF336490),
                ),
            painter = painterResource(R.drawable.find_by_actor)
        )
    }
}


@ThemeAndLocalePreviews
@Composable
private fun SearchSuggestionHubPreview() {
    SearchSuggestionHub()
}


@Stable
fun Modifier.dropShadow(
    color: Color = Color.Black.copy(0.1f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blur != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = blur.toPx(),
                radiusY = blur.toPx(),
                paint
            )
        }
    }
)