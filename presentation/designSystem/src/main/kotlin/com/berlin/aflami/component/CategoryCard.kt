package com.berlin.aflami.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    mediaType: String,
    mediaImgId: Int
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
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp, start = 8.dp),
            text = mediaType,
            style = Theme.textStyle.label.medium,
            color = Theme.color.textColors.title
        )
        Image(
            modifier = Modifier
                .padding(start = 42.dp, bottom = 8.dp)
                .width(64.dp)
                .height(71.dp)
                .offset(y = (-8).dp),
            painter = painterResource(mediaImgId),
            contentDescription = stringResource(R.string.category_img),
        )


    }


}

@ThemeAndLocalePreviews
@Composable
fun CategoryCardPreview() {
    AflamiTheme {
        CategoryCard(
            modifier = Modifier,
            stringResource(R.string.action),
            R.drawable.action_img
        )
    }
}