package com.berlin.aflami.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun Chips(
    title: String,
    icon: Painter
) {

    var isSelected by rememberSaveable { mutableStateOf(true) }

    val background by animateColorAsState(
        targetValue = if (isSelected) Theme.color.secondary else Theme.color.surfaceHigh
    )

    val border by animateColorAsState(
        targetValue = if (isSelected) Theme.color.stroke else Color.Transparent
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.textColors.onPrimary else Theme.color.textColors.hint
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    background,
                )
                .border(
                    1.dp,
                    shape = RoundedCornerShape(16.dp), color = border
                )
                .clickable {
                    isSelected = !isSelected
                },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = icon,
                contentDescription = stringResource(id = R.string.icon_cd),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(iconColor)
            )
        }

        Text(
            title,
            color = Theme.color.textColors.body,
            style = Theme.textStyle.label.small,
            textAlign = TextAlign.Center,
            modifier = Modifier.height(32.dp)
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun ChipsPreview() {
    AflamiTheme {
        Chips(
            title = "All",
            icon = painterResource(R.drawable.all_movies)
        )
    }
}