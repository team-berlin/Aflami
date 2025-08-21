package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SeasonsHeader(
    modifier: Modifier,
    seasonNumber: String,
    episodeCount: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
            .clickable { onToggleExpand() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "${stringResource(com.berlin.ui.R.string.season)} $seasonNumber",
            style = Theme.textStyle.title.small,
            color = Theme.color.textColors.title,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$episodeCount ${stringResource(com.berlin.ui.R.string.episode)}",
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint,
            )
            Icon(
                painter = if (isExpanded) painterResource(R.drawable.arrow_up) else painterResource(
                    R.drawable.arrow_down
                ),
                contentDescription = stringResource(R.string.icon_cd),
                modifier = Modifier.size(20.dp),
                tint = Theme.color.textColors.title
            )
        }
    }
}
