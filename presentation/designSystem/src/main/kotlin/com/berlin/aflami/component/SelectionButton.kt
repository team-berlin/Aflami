package com.berlin.aflami.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SelectionButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    iconId: Int,
    selectionTitleId: Int,
    subTitleId:Int?=null

) {

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) Theme.color.primaryVariant
        else Theme.color.surface,
    )

    val borderColor by animateColorAsState(
        targetValue = if (selected) Theme.color.primaryVariant
        else Theme.color.stroke,
    )
    val iconColor by animateColorAsState(
        targetValue = if (selected) Theme.color.primary
        else Theme.color.textColors.body,
    )

    Row(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                tint = iconColor
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(selectionTitleId),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.body,
                )
                subTitleId?.let {
                    Text(
                        text = stringResource(subTitleId),
                        style = Theme.textStyle.label.small,
                        color = Theme.color.textColors.hint,
                    )
                }

            }
            RadioButton(
                selected = selected,
                onClick = { onClick() },
                modifier = Modifier,
                enabled = true,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Theme.color.primary,
                    unselectedColor = iconColor
                )
            )

        }


    }
}


@ThemeAndLocalePreviews
@Composable
private fun LanguageSelectioButtonPreview() {

    AflamiTheme {
        var language by remember { mutableStateOf("English") }

        Column {

            SelectionButton(
                selected = language == "English",
                onClick = { language = "English" },
                iconId = R.drawable.english,
                selectionTitleId = R.string.english
            )
            SelectionButton(
                selected = language == "Arabic",
                onClick = { language = "Arabic" },
                iconId = R.drawable.arabic,
                selectionTitleId = R.string.arabic
            )
        }
    }

}

@ThemeAndLocalePreviews
@Composable
private fun ThemeSelectionButtonPreview() {
    AflamiTheme {
        var theme by remember { mutableStateOf("Light") }

        Column {
            SelectionButton(
                selected = theme == "Light",
                onClick = { theme = "Light" },
                iconId = R.drawable.light,
                selectionTitleId = R.string.light
            )
            SelectionButton(
                selected = theme == "Dark",
                onClick = { theme = "Dark" },
                iconId = R.drawable.dark,
                selectionTitleId = R.string.dark
            )
        }
    }
}
