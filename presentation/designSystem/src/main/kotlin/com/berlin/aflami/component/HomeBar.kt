package com.berlin.aflami.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun HomeBar(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onclickSearch: () -> Unit,
) {
    val iconBoxModifier = Modifier
        .size(40.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(
            color = Theme.color.primaryVariant
        )
        .border(
            1.dp, Theme.color.stroke, shape = RoundedCornerShape(12.dp)
        )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = iconBoxModifier,
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.home_logo),
                contentDescription = "$title logo",
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
        ) {
            Text(
                text = title,
                color = Theme.color.textColors.title,
                style = Theme.textStyle.body.large,
            )
            Text(
                text = description,
                color = Theme.color.textColors.body,
                style = Theme.textStyle.label.small,
            )
        }
        Box(
            modifier = iconBoxModifier.clickable { onclickSearch() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.search),
                contentDescription = "Search Icon",
                tint = Theme.color.textColors.body
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun HomeBarPreview() {
    AflamiTheme {
        Column(
            Modifier
                .background(Theme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeBar(
                title = stringResource(R.string.aflami_title),
                description = stringResource(R.string.aflami_title),
                onclickSearch = {}
            )
        }
    }
}