package com.berlin.aflami.screens.mediadetails.components

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.aflami.ui.color.ExtraColors.darkPurpleLinearGradient
import com.berlin.aflami.ui.theme.Theme

@Composable
fun RateDialog(
    onDismiss: () -> Unit,
    onRate: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedRating by remember { mutableIntStateOf(0) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier
                .padding(16.dp)
                .background(
                    color = Theme.color.surface,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 280.dp, max = 340.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rate it",
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.background(Theme.color.surfaceHigh,shape = RoundedCornerShape(12.dp))) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Theme.color.textColors.title)
                    }
                }
                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Select how much you like it",
                    style = Theme.textStyle.body.medium,
                    color = Theme.color.textColors.body,
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 1..5) {
                        Icon(
                            painter = painterResource(
                                id = if (i <= selectedRating)
                                    com.berlin.designsystem.R.drawable.ic_star_filled
                                else
                                    com.berlin.designsystem.R.drawable.ic_star_outline
                            ),
                            contentDescription = "Star $i",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable { selectedRating = i },
                            tint = Theme.color.statusColors.yellowAccent
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))

                val isEnabled = selectedRating > 0
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            brush = (if (isEnabled)
                                darkPurpleLinearGradient
                            else
                                Brush.linearGradient(listOf(Theme.color.disable,Theme.color.disable))),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable(enabled = isEnabled) {
                            onRate(selectedRating)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Submit",
                        color = if (isEnabled) Theme.color.textColors.onPrimary else Theme.color.stroke,
                        style = Theme.textStyle.label.large,
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun RateDialogPreview() {
    RateDialog(onDismiss = {}, onRate = {})
}