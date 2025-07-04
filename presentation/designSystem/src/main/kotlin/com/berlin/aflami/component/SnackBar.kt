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
import androidx.compose.ui.graphics.Color
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
    text: String,
    iconPainter: Painter,
    iconColor: Color,
    shadowColor: Color,
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
                color = shadowColor,
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
                contentDescription = stringResource(R.string.icon),
                tint = iconColor,
            )

            Text(
                text = text,
                style = Theme.textStyle.body.medium,
                color = Theme.color.textColors.body
            )
        }
    }
}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF0D090B,
)
@Composable
fun SnackBarSuccessPreview() {
    AflamiTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SnackBar(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.list_added_success),
                iconPainter = painterResource(id = R.drawable.success),
                shadowColor = darkReddishGreen12,
                iconColor = Theme.color.statusColors.greenAccent
            )
        }

    }
}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF0D090B
)
@Composable
fun SnackBarErrorPreview() {
    AflamiTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SnackBar(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.list_error),
                iconPainter = painterResource(id = R.drawable.error),
                iconColor = Theme.color.statusColors.redAccent,
                shadowColor = darkReddishPink12
            )
        }
    }
}
