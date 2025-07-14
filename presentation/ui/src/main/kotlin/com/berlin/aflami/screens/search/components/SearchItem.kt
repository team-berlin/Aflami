package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R


@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    text: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Icon(
            painter = painterResource(R.drawable.clock),
            contentDescription = "Clock",
            tint = Theme.color.textColors.hint
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            style = Theme.textStyle.body.medium,
            color = Theme.color.textColors.title
        )
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.cancel),
            contentDescription = "cancel",
            tint = Theme.color.textColors.hint,
            modifier = Modifier.clickable { onDeleteClick() }

        )
    }
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Theme.color.stroke
    )
}