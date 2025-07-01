package com.example.aflami

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aflami.designsystem.theme.AflamiTheme
import com.example.aflami.designsystem.theme.Theme

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
            modifier = Modifier.padding(end = 8.dp),
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
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "ar",
    backgroundColor = 0xFF0D090B
)
@Preview(
    showBackground = true,
    locale = "en",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFAF5F7
)
fun SectionTitlePreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(), title = stringResource(R.string.trending)
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "ar",
    backgroundColor = 0xFF0D090B
)
@Preview(
    showBackground = true,
    locale = "en",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFAF5F7
)

fun SectionTitleWithIconPreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.trending),
            icon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.fire),
                    tint = Theme.color.textColors.title,
                    contentDescription = "trending"
                )
            })
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "ar",
    backgroundColor = 0xFF0D090B
)
@Preview(
    showBackground = true,
    locale = "en",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFAF5F7
)

fun SectionTitleWithTrailingTextPreview() {
    AflamiTheme {
        SectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.trending),
            icon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.fire),
                    tint = Theme.color.textColors.title,
                    contentDescription = "trending"
                )
            },
            trailingText =
                stringResource(R.string.all),
        )
    }
}