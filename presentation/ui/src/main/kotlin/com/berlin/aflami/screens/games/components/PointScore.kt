package com.berlin.aflami.screens.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun PointScore(
    userPoints: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(CircleShape)
            .background(
                Brush.verticalGradient(
                    colors = Theme.color.gradientColors.pointsOverly,
                )
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = stringResource( R.string.pts,userPoints),
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.onPrimary,
        )
        Icon(
            painter = painterResource(R.drawable.points_star),
            contentDescription = "Star",
            tint = Theme.color.textColors.onPrimary,
            modifier = Modifier.size(16.dp)
        )
    }
}