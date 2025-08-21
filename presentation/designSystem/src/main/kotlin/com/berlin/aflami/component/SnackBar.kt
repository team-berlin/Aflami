package com.berlin.aflami.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.ui.color.ExtraColors.darkReddishGreen12
import com.berlin.aflami.ui.color.ExtraColors.darkReddishPink12
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R
import kotlinx.coroutines.delay

@Composable
fun SnackBar(
    isVisible: Boolean = false,
    status: SnackBarStatus,
    text: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2000,
    onDismiss: () -> Unit = {},
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            visible = true
            delay(durationMillis)
            visible = false
            onDismiss()
        }
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    alpha = 0.12f,
                    offsetX = 0.dp,
                    offsetY = (4).dp,
                    blur = 6.dp,
                    color = when (status) {
                        SnackBarStatus.SUCCESS -> darkReddishGreen12
                        SnackBarStatus.ERROR -> darkReddishPink12
                    },
                )
        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.95f)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = Theme.color.stroke,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Theme.color.surfaceHigh)
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = stringResource(R.string.icon_cd),
                    tint = when (status) {
                        SnackBarStatus.SUCCESS -> Theme.color.statusColors.greenAccent
                        SnackBarStatus.ERROR -> Theme.color.statusColors.redAccent
                    },
                )

                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = Theme.textStyle.body.medium,
                    color = Theme.color.textColors.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

enum class SnackBarStatus {
    SUCCESS,
    ERROR
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0D090B,
)
@Composable
private fun SnackBarSuccessPreview() {
    AflamiTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SnackBar(
                status = SnackBarStatus.SUCCESS,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.list_added_success),
                iconPainter = painterResource(id = R.drawable.success),
                onDismiss = { null }

            )
        }

    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0D090B
)
@Composable
private fun SnackBarErrorPreview() {
    AflamiTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SnackBar(
                status = SnackBarStatus.ERROR,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.list_error),
                iconPainter = painterResource(id = R.drawable.error),
                onDismiss = { null }
            )
        }
    }
}
