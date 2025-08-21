package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.screens.lists.component.Dialog
import com.berlin.aflami.ui.color.ExtraColors.shadowPink24
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun LoginRequiredDialog(
    onLoginClick: () -> Unit,
    onDismiss: () -> Unit,
    title: String = "Login Required",
    description: String = "",
) {
    Dialog(
        onDismiss = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .background(
                    Theme.color.surface,
                    RoundedCornerShape(24.dp)
                )
                .padding(12.dp)
        ) {
            Column(
                Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.background(
                            Theme.color.surfaceHigh,
                            shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Theme.color.textColors.title
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .dropShadow(
                            shape = RoundedCornerShape(24.dp),
                            color = shadowPink24,
                            offsetY = 4.dp,
                            blur = 12.dp
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .border(1.dp, Theme.color.stroke)
                ) {
                    Image(
                        painter = painterResource(id = com.berlin.designsystem.R.drawable.no_review_image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.height(12.dp))

                Text(
                    text = description,
                    style = Theme.textStyle.body.medium,
                    color = Theme.color.textColors.body,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(24.dp))

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Theme.color.primaryVariant)
                        .clickable { onLoginClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.login_dialog),
                        color = Theme.color.primary,
                        style = Theme.textStyle.label.large,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginRequiredDialogPreview() {
    LoginRequiredDialog(
        onLoginClick = {},
        onDismiss = {}
    )
}