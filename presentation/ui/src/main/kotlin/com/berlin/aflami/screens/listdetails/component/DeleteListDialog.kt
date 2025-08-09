package com.berlin.aflami.screens.listdetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun DeleteListDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    listId: Int,
    onConfirm: (listId: Int) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogTitleBar(
                titleResource = R.string.delete_list,
                modifier = modifier.fillMaxWidth(),
                onDismiss = onDismiss
            )
            Image(
                painter = painterResource(R.drawable.deletealert),
                contentDescription = stringResource(R.string.delete_list),
                modifier = modifier.height(100.dp),
                contentScale = ContentScale.FillHeight
            )

            Text(
                modifier = modifier.padding(top = 12.dp),
                text = stringResource(R.string.delete_confirm),
                style = Theme.textStyle.title.small,
                color = Theme.color.textColors.body,
                textAlign = TextAlign.Center
            )

            PrimaryButton(
                onClick = { onConfirm(listId) },
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                containerColor = Theme.color.primaryVariant
            ) {
                Text(
                    stringResource(R.string.delete),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.primary
                )
            }

        }
    }
}