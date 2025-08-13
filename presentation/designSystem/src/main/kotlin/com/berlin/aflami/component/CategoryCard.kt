package com.berlin.aflami.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    text: String,
    image: Painter,
    onClick: () -> Unit = {}
) {

    val strokColor = Theme.color.stroke
    Row(
        modifier
            .background(Theme.color.surfaceHigh, RoundedCornerShape(16.dp))
            .drawBehind {
                val stroke = 1.dp.toPx()
                val radius = 16.dp.toPx()
                drawRoundRect(
                    color = strokColor,
                    size = size,
                    cornerRadius = CornerRadius(radius, radius),
                    style = Stroke(width = stroke)
                )
            }
            .clickable {
                onClick()
            }
            ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp, start = 8.dp),
            text = text,
            style = Theme.textStyle.label.medium,
            color = Theme.color.textColors.title
        )
        Image(
            modifier = Modifier
                .padding(start = 42.dp)
                .offset(y = (-8).dp)
                .size(height = 71.dp, width = 64.dp)
            ,
            painter = image,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.category_img_content))
    }


}

@ThemeAndLocalePreviews
@Composable
fun CategoryCardPreview() {
    AflamiTheme {
        CategoryCard(
            modifier = Modifier,
            stringResource(R.string.action),
            painterResource(R.drawable.adventure_img)
        )
    }
}