package com.berlin.aflami.screens.games.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.component.buttons.ButtonState
import com.berlin.aflami.component.buttons.PrimaryButton
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

@Composable
fun LevelDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = listOf(
        stringResource(com.berlin.ui.R.string.easy),
        stringResource(com.berlin.ui.R.string.medium),
        stringResource(com.berlin.ui.R.string.hard),
    )
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    val details = when (selectedIndex) {
        0 -> stringResource(com.berlin.ui.R.string.level_easy)
        1 -> stringResource(com.berlin.ui.R.string.level_medium)
        2 -> stringResource(com.berlin.ui.R.string.level_hard)
        else -> ""
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = modifier
                .background(Theme.color.surface, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Column(
                Modifier
                    .widthIn(min = 328.dp, max = 360.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(com.berlin.ui.R.string.choose_difficulty_level),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                Theme.color.surfaceHigh,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Theme.color.textColors.title
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    genres.forEachIndexed { index, label ->
                        val isSelected = selectedIndex == index
                        GenersChip(
                            label = label,
                            isSelected = isSelected,
                            isClickable = true,
                            onClick = {
                                selectedIndex = if (isSelected) null else index
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = selectedIndex != null,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 300,
                        )
                    ) + expandVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 300,
                        )
                    ) + shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                        )
                    )

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Theme.color.surfaceHigh,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = com.berlin.ui.R.drawable.idea),
                            contentDescription = null,
                            tint = Theme.color.statusColors.yellowAccent
                        )
                        Text(
                            text = details,
                            style = Theme.textStyle.label.small,
                            color = Theme.color.statusColors.yellowAccent,
                        )
                    }
                }

                if (selectedIndex != null) {
                    PrimaryButton(
                        onClick = {},
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(com.berlin.ui.R.string.lets_start),
                            style = Theme.textStyle.label.large,
                            color = Theme.color.textColors.onPrimary,
                        )
                    }
                } else {
                    PrimaryButton(
                        onClick = {},
                        state = ButtonState.DISABLED,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(com.berlin.ui.R.string.lets_start),
                            style = Theme.textStyle.label.large,
                            color = Theme.color.stroke
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LevelDialogPreview() {
    AflamiTheme{
        LevelDialog(
            onDismiss = {}
        )
    }
}