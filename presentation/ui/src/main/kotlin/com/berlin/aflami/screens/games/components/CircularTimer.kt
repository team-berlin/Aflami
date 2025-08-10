package com.berlin.aflami.screens.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import kotlinx.coroutines.delay


@Composable
fun CircularProgress(
    totalTime: Int,
    currentTime: Int,
    progressSize: Dp,
    normalColor: Color = Theme.color.statusColors.greenAccent,
    warningColor: Color = Theme.color.statusColors.redAccent,
) {
    val progress = currentTime / totalTime.toFloat()
    val color = if (currentTime <= 5) warningColor else normalColor

    CircularProgressIndicator(
        progress = { progress },
        modifier = Modifier.size(progressSize),
        color = color,
        strokeWidth = 2.dp,
        trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        strokeCap = StrokeCap.Round,
    )
}

@Composable
fun CountdownTimer(totalTime: Int) {
    var currentTime by remember { mutableStateOf(totalTime) }

    LaunchedEffect(Unit) {
        while (currentTime > 0) {
            delay(1000)
            currentTime--
        }
    }
    CircularProgress(
        totalTime = totalTime,
        currentTime = currentTime,
        progressSize = 40.dp,
        normalColor = Theme.color.statusColors.greenAccent,
        warningColor = Theme.color.statusColors.redAccent,
    )
}

@Composable
fun CountdownCircularProgress(
    timePerSecond: Int,
    modifier: Modifier=Modifier
) {
    Box(
        modifier = modifier.size(40.dp).background(Theme.color.primaryVariant,
            RoundedCornerShape(100.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${timePerSecond}s",
            style = Theme.textStyle.label.small,
            color = Theme.color.statusColors.greenAccent
        )
        CountdownTimer(timePerSecond)
    }
}

@Preview(showBackground = true)
@Composable
fun CircularTimerPreview() {
    CountdownCircularProgress(45)
}
