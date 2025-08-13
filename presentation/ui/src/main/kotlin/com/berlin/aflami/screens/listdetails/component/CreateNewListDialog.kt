package com.berlin.aflami.screens.listdetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.component.TextField
import com.berlin.aflami.screens.lists.component.Dialog
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun CreateNewListDialog(
    modifier: Modifier = Modifier,
    listName: TextFieldValue = TextFieldValue(""),
    onListNameChanged: (TextFieldValue) -> Unit = {},
    onCreateListClick: (TextFieldValue) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Dialog(
        modifier = modifier,
        onDismiss = onDismiss,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(12.dp),
        ) {
            DialogTitleBar(
                titleResource = R.string.create_new_list, onDismiss = onDismiss
            )
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                text = listName,
                onValueChange = { newValue ->
                    onListNameChanged(newValue)
                },
                isEnabled = true,
                maxLines = 1,
                hintText = stringResource(R.string.my_favorite),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() },
                ),
                leadingIcon = R.drawable.nav_lists,
            )

            PrimaryButton(
                onClick = {
                    onCreateListClick(listName)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                containerColor = Theme.color.primary,
            ) {
                Text(
                    stringResource(R.string.create),
                    style = Theme.textStyle.label.large, color = Theme.color.textColors.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCreateNewListDialog() {
//    CreateNewListDialog(
//        listName = "My Favorite",
//        onListNameChanged = {},
//        onCreateListClick = {},
//        onDismiss = {},
//    )
}