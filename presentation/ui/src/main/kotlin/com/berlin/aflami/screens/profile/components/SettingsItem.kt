package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun SettingsItem(
    icon: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = icon,
            contentDescription = title,
            tint = Theme.color.textColors.body,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Theme.color.stroke, RoundedCornerShape(12.dp))
                .background(Theme.color.surfaceHigh)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = Theme.textStyle.label.large,
            color = Theme.color.textColors.title,
        )
        Spacer(modifier = Modifier.weight(1f))



        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.body,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.size(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(R.drawable.navigate),
                contentDescription = "Navigate",
                tint = Theme.color.textColors.hint,
            )
        }
    }
}