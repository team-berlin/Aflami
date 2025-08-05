package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun AvatarSection(
    userAvatar: String,
    userName: String,
    userPoints: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = userAvatar,
            contentDescription = "User Avatar",
            error = painterResource(R.drawable.profile_avatar),
            placeholder = painterResource(R.drawable.profile_avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, Theme.color.stroke, RoundedCornerShape(24.dp))
        )
        Text(
            text = "@$userName",
            style = Theme.textStyle.label.medium,
            color = Theme.color.textColors.body
        )

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
                text = "$userPoints pts.",
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
}