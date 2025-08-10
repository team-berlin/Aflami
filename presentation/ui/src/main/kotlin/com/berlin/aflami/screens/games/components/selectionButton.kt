package com.berlin.aflami.screens.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun SelectionItem(
    guessName: String,
    modifier: Modifier = Modifier,
    isSelected:Boolean?=null,
    checkBgColor: Color=Theme.color.surfaceHigh,
    borderCheck: Color=Theme.color.stroke,
    onSelectItem: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderCheck,
                shape = RoundedCornerShape(16.dp),
            )
            .clip(RoundedCornerShape(16.dp))
            .background(checkBgColor)
            .clickable(onClick = onSelectItem)
            .padding(vertical = 16.dp, horizontal = 12.dp),
    ) {
        Text(
            text = guessName,
            style = Theme.textStyle.label.large,
            color = Theme.color.textColors.body,
        )
        val icon = when (isSelected) {
            true -> R.drawable.check_mark
            false -> R.drawable.wrong_check
            null -> R.drawable.radio_button
        }
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = borderCheck ,
            modifier = Modifier.border(1.dp,borderCheck, RoundedCornerShape(100.dp))
        )
    }
}
