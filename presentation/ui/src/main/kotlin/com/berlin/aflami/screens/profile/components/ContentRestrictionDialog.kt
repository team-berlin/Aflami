package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.aflami.component.SelectionButton
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun ContentRestrictionDialog(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onDismiss: () -> Unit,
    onFirstOptionClick: () -> Unit = {},
    onSecondOptionClick: () -> Unit = {},
    onThirdOptionClick: () -> Unit = {},
    isFirstOptionSelected: Boolean = false,
    isSecondOptionSelected: Boolean = false,
    isThirdOptionSelected: Boolean = false,
    title: Int,
    firstOptionTitleRes: Int? = null,
    secondOptionTitleRes: Int? = null,
    thirdOptionTitleRes: Int? = null,
    firstOptionIconRes: Int? = null,
    secondOptionIconRes: Int? = null,
    thirdOptionIconRes: Int? = null,
    firstOptionSubTitleIdRes: Int? = null,
    secondOptionSubTitleIdRes: Int? = null,
    thirdOptionSubTitleIdRes: Int? = null,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = stringResource(id = title),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.background(
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

                Spacer(modifier = Modifier.height(12.dp))

                if (firstOptionIconRes != null && firstOptionTitleRes != null) {
                    SelectionButton(
                        selected = isFirstOptionSelected,
                        onClick = onFirstOptionClick,
                        iconId = firstOptionIconRes,
                        selectionTitleId = firstOptionTitleRes,
                        subTitleId = firstOptionSubTitleIdRes
                    )
                }
                if (secondOptionIconRes != null && secondOptionTitleRes != null) {
                    SelectionButton(
                        selected = isSecondOptionSelected,
                        onClick = onSecondOptionClick,
                        iconId = secondOptionIconRes,
                        selectionTitleId = secondOptionTitleRes,
                        subTitleId = secondOptionSubTitleIdRes
                    )
                }

                if (thirdOptionIconRes != null && thirdOptionTitleRes != null) {
                    SelectionButton(
                        selected = isThirdOptionSelected,
                        onClick = onThirdOptionClick,
                        iconId = thirdOptionIconRes,
                        selectionTitleId = thirdOptionTitleRes,
                        subTitleId = thirdOptionSubTitleIdRes
                    )
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush
                                .linearGradient(
                                    Theme.color.gradientColors.primaryGradient,
                                    end = Offset(0f, Float.POSITIVE_INFINITY)
                                )
                        )
                        .clickable { onSaveClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp),
                        text = stringResource(com.berlin.ui.R.string.save),
                        color = Theme.color.textColors.onPrimary,
                        style = Theme.textStyle.label.large,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ContentRestrictionDialogPreview() {
    ContentRestrictionDialog(
        title = com.berlin.ui.R.string.app_theme,
        firstOptionTitleRes = com.berlin.ui.R.string.dark,
        secondOptionTitleRes = com.berlin.ui.R.string.light,
        firstOptionIconRes = R.drawable.dark,
        secondOptionIconRes = R.drawable.light,
        onSaveClick = {},
        onDismiss = {}
    )
}
