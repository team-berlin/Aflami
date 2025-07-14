package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun IconButton(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(10.dp),
    withBorder: Boolean = false,
    containerColor: Color = Theme.color.surfaceHigh,
    tint: Color = Theme.color.textColors.body,
    shape: Shape = RoundedCornerShape(12.dp),
    onClick: () -> Unit = {}
) {
    val borderModifier = if (withBorder) {
        Modifier.border(width = 1.dp, color = Theme.color.stroke, shape = shape)
    } else {
        Modifier
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                shape = shape,
                color = containerColor,
            )
            .then(borderModifier)
            .clickable(onClick = onClick),
    ) {
        Icon(
            painter = painter,
            tint = tint,
            contentDescription = contentDescription,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun IconPreview() {
    AflamiTheme {
        IconButton(
            painter = painterResource(R.drawable.search),
            contentDescription = "search",
            containerColor = Theme.color.primaryVariant,
            tint = Theme.color.textColors.body,
            withBorder = true,
            paddingValues = PaddingValues(8.dp),
            modifier = Modifier.padding(8.dp)
        )
    }
}