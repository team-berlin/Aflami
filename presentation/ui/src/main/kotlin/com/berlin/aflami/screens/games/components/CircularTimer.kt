package com.berlin.aflami.screens.games.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

@Composable
fun CountdownCircularProgress(
    totalTime: Int,
    currentTime: Int,
    modifier: Modifier = Modifier,
) {
    val textColor by animateColorAsState(
        targetValue = when {
            currentTime <= 5 -> Theme.color.statusColors.redAccent
            else -> Theme.color.statusColors.greenAccent
        },
        label = "text_color"
    )

    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                Theme.color.primaryVariant,
                RoundedCornerShape(100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${currentTime}s",
            style = Theme.textStyle.label.small,
            color = textColor
        )
        CircularProgress(
            totalTime = totalTime,
            currentTime = currentTime,
            progressSize = 40.dp,
            normalColor = Theme.color.statusColors.greenAccent,
            warningColor = Theme.color.statusColors.redAccent,
        )
    }
}

@Composable
fun CircularProgress(
    totalTime: Int,
    currentTime: Int,
    progressSize: Dp,
    normalColor: Color = Theme.color.statusColors.greenAccent,
    warningColor: Color = Theme.color.statusColors.redAccent,
) {
    val progress = if (totalTime > 0) currentTime.toFloat() / totalTime.toFloat() else 0f

    val progressColor by animateColorAsState(
        targetValue = if (currentTime <= 5) warningColor else normalColor,
        label = "progress_color"
    )

    CircularProgressIndicator(
        progress = { progress },
        modifier = Modifier.size(progressSize),
        color = progressColor,
        strokeWidth = 2.dp,
        trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        strokeCap = StrokeCap.Round,
    )
}
