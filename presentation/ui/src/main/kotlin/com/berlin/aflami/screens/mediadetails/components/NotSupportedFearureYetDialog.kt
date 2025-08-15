package com.berlin.aflami.screens.mediadetails.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.lists.component.Dialog
import com.berlin.aflami.ui.theme.Theme

@Composable
fun NotSupportedFeatureDialog(
    onDismiss: () -> Unit,
    description: String = "",
) {
    Dialog(
        onDismiss = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.surface, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Text(
                text = description,
                style = Theme.textStyle.body.large,
                color = Theme.color.textColors.body,
            )
        }
    }
}