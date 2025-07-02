package com.berlin.aflami.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun SectionTitle(
    modifier: Modifier,
    @StringRes title: Int,
    icon: @Composable (() -> Unit)? = null,
    @StringRes trailingText: Int? = null
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = stringResource(title),
            style = Theme.textStyle.headline.small,
            color = Theme.color.textColors.title
        )
        icon?.invoke()
        trailingText?.run {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(trailingText),
                style = Theme.textStyle.label.medium,
                color = Theme.color.primary
            )
        }
    }
}

@Composable
@ThemeAndLocalePreviews
fun SectionTitlePreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(), title = R.string.trending
        )
    }
}

@Composable
@ThemeAndLocalePreviews
fun SectionTitleWithIconPreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.trending,
            icon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.trending),
                    tint = Theme.color.textColors.title,
                    contentDescription = "trending"
                )
            })
    }
}

@Composable
@ThemeAndLocalePreviews
fun SectionTitleWithTrailingTextPreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.trending,
            icon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.trending),
                    tint = Theme.color.textColors.title,
                    contentDescription = "trending"
                )
            },
            trailingText =
                R.string.all,
        )
    }
}