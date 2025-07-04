package com.berlin.aflami.component

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
    title: String,
    icon: @Composable (() -> Unit)? = null,
    trailingText: String? = null
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(

            text = title,
            style = Theme.textStyle.headline.small,
            color = Theme.color.textColors.title
        )
        icon?.invoke()
        trailingText?.run {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = trailingText,
                style = Theme.textStyle.label.medium,
                color = Theme.color.primary
            )
        }
    }
}

@Composable
@ThemeAndLocalePreviews
private fun SectionTitlePreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(), title = stringResource(R.string.trending)
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun SectionTitleWithIconPreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.trending),
            icon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.trending),
                    tint = Theme.color.textColors.title,
                    contentDescription = stringResource(R.string.trending)
                )
            })
    }
}

@Composable
@ThemeAndLocalePreviews
private fun SectionTitleWithTrailingTextPreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.trending),
            icon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.trending),
                    tint = Theme.color.textColors.title,
                    contentDescription = stringResource(R.string.trending)
                )
            },
            trailingText =
                stringResource(R.string.all),
        )
    }
}