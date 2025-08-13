package com.berlin.aflami.screens.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

@Composable
fun NoPointDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier
                .background(
                    Theme.color.surface,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            // TODO

        }
    }

}


@Preview
@Composable
fun NoPointDialogPreview(modifier: Modifier = Modifier) {
    AflamiTheme {
        NoPointDialog({})
    }
}