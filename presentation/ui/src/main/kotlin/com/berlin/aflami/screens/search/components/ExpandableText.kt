package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.ui.textstyle.IBM
import com.berlin.aflami.ui.theme.Theme

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    maxSize: Int = 5,
) {
    var expanded by remember { mutableStateOf(false) }
    var hasVisualOverflow by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Text(
            text = text,
            color = Theme.color.textColors.body,
            maxLines = if (expanded) Int.MAX_VALUE else maxSize,
            onTextLayout = { hasVisualOverflow = it.hasVisualOverflow },
            style = Theme.textStyle.body.small,
        )
        if (hasVisualOverflow) {
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .background(Theme.color.surface)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { expanded = !expanded }
                        ),
                    text = " Read more",
                    color = Theme.color.primary,
                    style = Theme.textStyle.label.medium.copy(
                        fontFamily = IBM,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpandableTextPreview() {
    ExpandableText(
        text = "Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this rather disappointingly trundles along for the guts of two hours. It’s starts, Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this rather disappointingly trundles along for the guts of two hours. It’s starts"
    )
}