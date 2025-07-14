package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun HomeBar(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    onSearchClicked: () -> Unit = {}
) {
    TopBar(modifier = modifier, title = {
        Text(
            text = stringResource(R.string.aflami_title),
            color = Theme.color.textColors.title,
            style = Theme.textStyle.label.large
        )
    }, containerColor = containerColor, subTitle = {
        Text(
            text = stringResource(R.string.aflami_description),
            color = Theme.color.textColors.body,
            style = Theme.textStyle.label.small
        )
    }, leadingIcon = {
        IconButton(
            painter = painterResource(R.drawable.home_logo),
            contentDescription = null,
            containerColor = Theme.color.primaryVariant,
            tint = Color.Unspecified,
            paddingValues = PaddingValues(horizontal = 6.dp, vertical = 9.dp),
            withBorder = true,
        )
    }, trailingIcon = {
        IconButton(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            containerColor = Theme.color.primaryVariant,
            tint = Theme.color.textColors.body,
            paddingValues = PaddingValues(8.dp),
            withBorder = true,
            onClick = onSearchClicked
        )
    })
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
                containerColor = Theme.color.surface, onSearchClicked = {})
        }
    }
}