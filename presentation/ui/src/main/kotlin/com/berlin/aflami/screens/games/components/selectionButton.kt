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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun SelectionItem(
    guessName: String,
    modifier: Modifier = Modifier,
    isSelected:Boolean?=null,
    onSelectItem: () -> Unit = {},
) {

    val checkBgColor = when (isSelected) {
        true -> Theme.color.statusColors.greenVariant
        false -> Theme.color.statusColors.redVariant
        null -> Theme.color.surface
    }
    val borderCheck = when (isSelected) {
        true -> Theme.color.statusColors.greenAccent
        false -> Theme.color.statusColors.redAccent
        null -> Theme.color.stroke
    }
    val icon = when (isSelected) {
        true -> R.drawable.check_mark
        false -> R.drawable.wrong_check
        null -> R.drawable.radio_button
    }
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
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if(isSelected==null)Theme.color.surfaceHigh else borderCheck ,
            modifier = Modifier.border(1.dp,borderCheck, RoundedCornerShape(100.dp))
        )
    }
}
