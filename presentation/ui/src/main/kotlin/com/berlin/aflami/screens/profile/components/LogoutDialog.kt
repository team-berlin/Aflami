package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.berlin.aflami.ui.theme.Theme

@Composable
fun LogoutDialog(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
    onDismiss: () -> Unit,
    title: Int,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .background(Theme.color.surface, RoundedCornerShape(24.dp))
                .padding(12.dp)
        ) {
            Column(
                Modifier
                    .widthIn(min = 328.dp, max = 360.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = stringResource(id = title),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.background(
                            Theme.color.surfaceHigh,
                            shape = RoundedCornerShape(12.dp)
                        )
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Theme.color.textColors.title
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(com.berlin.ui.R.drawable.yellow_sign),
                        contentDescription = null,
                        modifier = Modifier
                            .widthIn(max = 78.dp),
                    )
                }
                Text(
                    text = stringResource(com.berlin.ui.R.string.sure_to_continue),
                    style = Theme.textStyle.title.small,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    lineHeight = 24.sp
                )
                Text(
                    text = stringResource(com.berlin.ui.R.string.logout_msg),
                    style = Theme.textStyle.title.small,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 24.dp),
                    lineHeight = 24.sp

                )

                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = Theme.color.primaryVariant
                        )
                        .clickable { onLogoutClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp),
                        text = stringResource(com.berlin.ui.R.string.setting_dialog_logout),
                        color = Theme.color.statusColors.redAccent,
                        style = Theme.textStyle.label.large,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun LogoutDialogPreview() {
    LogoutDialog(
        title = com.berlin.ui.R.string.app_theme,

        onLogoutClick = {},
        onDismiss = {}
    )
}
