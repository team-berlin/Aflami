package com.berlin.aflami.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun DefaultBar(
    modifier: Modifier = Modifier,
    title: String = "",
    showNavigateBackButton: Boolean = true,
    firstOption: Painter? = null,
    firstOptionContentDescription: String? = null,
    lastOption: Painter? = null,
    lastOptionContentDescription: String? = null,
    containerColor: Color = Color.Unspecified,
    onFirstOptionClicked: () -> Unit = {},
    onLastOptionClicked: () -> Unit = {},
    onNavigateBackClicked: () -> Unit = {}
) {
    TopBar(
        modifier = modifier,
        containerColor = containerColor,
        title = title.takeIf { it.isNotBlank() }?.let { text ->
            {
                Text(
                    text = text,
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.large
                )
            }
        },
        leadingIcon = if (showNavigateBackButton) {
            {
                IconButton(
                    painter = painterResource(R.drawable.arrow_left),
                    tint = Theme.color.textColors.title,
                    contentDescription = null,
                    onClick = onNavigateBackClicked
                )
            }
        } else null,
        middleIcon = firstOption?.let { painter ->
            {
                IconButton(
                    painter = painter,
                    contentDescription = firstOptionContentDescription,
                    containerColor = Theme.color.primaryVariant,
                    tint = Theme.color.textColors.body,
                    paddingValues = PaddingValues(8.dp),
                    withBorder = true,
                    onClick = onFirstOptionClicked
                )
            }
        },
        trailingIcon = lastOption?.let { painter ->
            {
                IconButton(
                    painter = painter,
                    contentDescription = lastOptionContentDescription,
                    containerColor = Theme.color.primaryVariant,
                    tint = Theme.color.textColors.body,
                    paddingValues = PaddingValues(8.dp),
                    withBorder = true,
                    onClick = onLastOptionClicked
                )
            }
        }
    )
}

@ThemeAndLocalePreviews
@Composable
private fun DefaultBarPreview() {
    AflamiTheme {
        Column {
            DefaultBar(
                title = stringResource(R.string.my_account),
                showNavigateBackButton = false,
                firstOption = painterResource(R.drawable.star),
                lastOption = painterResource(R.drawable.sort),
                containerColor = Theme.color.surface
            )
            DefaultBar(
                title = stringResource(R.string.my_account),
                firstOption = painterResource(R.drawable.star),
                lastOption = painterResource(R.drawable.sort),
                containerColor = Theme.color.surface
            )
            DefaultBar(
                title = stringResource(R.string.my_account),
                lastOption = painterResource(R.drawable.sort),
                containerColor = Theme.color.surface
            )
        }
    }
}