package com.berlin.aflami.component

import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.ui.color.ExtraColors.darkReddishGreen12
import com.berlin.aflami.ui.color.ExtraColors.darkReddishPink12
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SnackBar(
    status: SnackBarStatus,
    text: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
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
            modifier
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
                style = Theme.textStyle.body.medium,
                color = Theme.color.textColors.body
            )
        }
    }
}

enum class SnackBarStatus {
    SUCCESS,
    ERROR
}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF0D090B,
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
                iconPainter = painterResource(id = R.drawable.success)
            )
        }

    }
}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF0D090B
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
                iconPainter = painterResource(id = R.drawable.error)
            )
        }
    }
}
