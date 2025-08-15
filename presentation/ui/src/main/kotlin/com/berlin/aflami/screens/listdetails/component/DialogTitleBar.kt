package com.berlin.aflami.screens.listdetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.berlin.aflami.component.IconButton
import com.berlin.aflami.ui.theme.Theme

@Composable
fun DialogTitleBar(
    titleResource: Int, modifier: Modifier = Modifier, onDismiss: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(titleResource),
            style = Theme.textStyle.title.large,
            color = Theme.color.textColors.title
        )
        IconButton(
            painter = painterResource(com.berlin.designsystem.R.drawable.cancel),
            contentDescription = null,
            onClick = { onDismiss() },
            tint = Theme.color.textColors.title
        )
    }
}