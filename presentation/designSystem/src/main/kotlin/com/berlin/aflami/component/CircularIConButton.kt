package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun CircularIConButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    hasDropShadow: Boolean = false,
    backgroundColor: Color = Theme.color.surfaceHigh,
    size: Int = 40,
    borderWidth: Int = 1,
    dropShadowColor: Color = Theme.color.primary,
    dropShadowAlpha: Float = 0.03f,
    blur: Int = 4,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .then(
                if (hasDropShadow)
                    Modifier.dropShadow(
                        shape = RoundedCornerShape(75),
                        color = dropShadowColor,
                        alpha = dropShadowAlpha,
                        blur = blur.dp,
                        offsetY = 4.dp
                    )
                else Modifier
            )
            .clip(RoundedCornerShape(50))
            .clickable { onClick() }
            .background(backgroundColor)
            .border(
                width = borderWidth.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(50)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = stringResource(R.string.icon_button),
            tint = Theme.color.primary,
            modifier = Modifier.size((size / 2).dp)
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun CircularIConButtonPreview() {
    AflamiTheme(isDarkTheme = false) {
        CircularIConButton(
            painter = painterResource(R.drawable.play),
            hasDropShadow = true,
            size = 64,
            borderWidth = 2,
            onClick = {}
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun CircularIConButtonDarkPreview() {
    AflamiTheme(isDarkTheme = true) {
        CircularIConButton(
            painter = painterResource(R.drawable.play),
            hasDropShadow = true,
            size = 64,
            borderWidth = 2,
            onClick = {}
        )
    }
}