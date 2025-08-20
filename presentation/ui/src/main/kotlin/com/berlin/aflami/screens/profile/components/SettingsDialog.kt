package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SettingsDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: Int = com.berlin.ui.R.string.settings,
    onFirstOptionClick: () -> Unit = {},
    onSecondOptionClick: () -> Unit = {},
    onThirdOptionClick: () -> Unit = {},
    firstOptionTitleRes: Int? = com.berlin.ui.R.string.setting_dialog_change_password,
    secondOptionTitleRes: Int? = com.berlin.ui.R.string.setting_dialog_content_restriction,
    thirdOptionTitleRes: Int? = com.berlin.ui.R.string.setting_dialog_logout_quote,
    firstOptionIconRes: Int? = R.drawable.ic_change_password,
    secondOptionIconRes: Int? = R.drawable.security,
    thirdOptionIconRes: Int? = R.drawable.ic_tired_face,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .background(Theme.color.surface, RoundedCornerShape(24.dp))
                .padding(12.dp)
        ) {
            Column(
                Modifier
                    .widthIn(min = 328.dp, max = 360.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = title),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.background(
                            Theme.color.surfaceHigh,
                            shape = RoundedCornerShape(12.dp),
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Theme.color.textColors.title,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (firstOptionIconRes != null && firstOptionTitleRes != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onFirstOptionClick() }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Theme.color.surfaceHigh,
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Theme.color.stroke,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = firstOptionIconRes),
                                    contentDescription = null,
                                    tint = Theme.color.textColors.hint
                                )
                            }

                            Text(
                                text = stringResource(id = firstOptionTitleRes),
                                style = Theme.textStyle.title.small,
                                color = Theme.color.textColors.body,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(id = com.berlin.ui.R.drawable.navigate),
                                contentDescription = null,
                                tint = Theme.color.textColors.hint
                            )

                        }
                    }
                }
                if (secondOptionIconRes != null && secondOptionTitleRes != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSecondOptionClick() }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Theme.color.surfaceHigh,
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Theme.color.stroke,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp),
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = secondOptionIconRes),
                                    contentDescription = null,
                                    tint = Theme.color.textColors.hint
                                )
                            }
                            Text(
                                text = stringResource(id = secondOptionTitleRes),
                                style = Theme.textStyle.title.small,
                                color = Theme.color.textColors.body,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(id = com.berlin.ui.R.drawable.navigate),
                                contentDescription = null,
                                tint = Theme.color.textColors.hint
                            )

                        }
                    }
                }
                if (thirdOptionIconRes != null && thirdOptionTitleRes != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Theme.color.surfaceHigh,
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Theme.color.stroke,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp),
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = thirdOptionIconRes),
                                    contentDescription = null,
                                    tint = Theme.color.textColors.hint
                                )
                            }
                            Text(
                                text = stringResource(id = thirdOptionTitleRes),
                                style = Theme.textStyle.title.small,
                                color = Theme.color.textColors.body,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onThirdOptionClick() },
                                text = stringResource(id = com.berlin.ui.R.string.setting_dialog_logout),
                                style = Theme.textStyle.label.medium,
                                color = Theme.color.primary,
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SettingsDialogPreview() {
    SettingsDialog(
        onDismiss = {}
    )
}