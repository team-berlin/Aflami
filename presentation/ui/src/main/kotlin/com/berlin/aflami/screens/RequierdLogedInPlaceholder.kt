package com.berlin.aflami.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun RequiredLoggedInPlaceholder(
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.profile_avatar),
            contentDescription = stringResource(R.string.profile),
            modifier = Modifier
                .height(80.dp)
                .dropShadow(
                    offsetY = 4.dp,
                    shape = RoundedCornerShape(24.dp),
                    blur = 12.dp,
                    color = Color(0x3DD85895),
                )
                .border(
                    width = 1.dp,
                    color = Theme.color.stroke,
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(24.dp))
                .clickable { onAvatarClick() },
            contentScale = ContentScale.FillHeight,

            )
        Text(
            text = stringResource(R.string.Please_login),
            style = Theme.textStyle.body.small,
            color = Theme.color.textColors.body,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
        )

        Button(
            onClick = { onClick() },
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            enabled = enable,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                Theme.color.primaryVariant
            ),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.login),
                style = Theme.textStyle.label.large,
                color = Theme.color.primary
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun RequiredLoggedInPlaceholderPreview() {
    RequiredLoggedInPlaceholder()
}