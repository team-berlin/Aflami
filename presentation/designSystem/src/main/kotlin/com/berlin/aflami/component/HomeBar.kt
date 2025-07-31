package com.berlin.aflami.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.berlin.aflami.ui.textstyle.nicomoji
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

import androidx.compose.runtime.getValue
@Composable
fun HomeBar(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    onSearchClicked: () -> Unit = {}
) {

    TopBar(modifier = modifier, title = {
        Text(
            text = stringResource(R.string.aflami_title),
            color = Theme.color.textColors.title,
            style = Theme.textStyle.label.medium.copy(
                fontFamily = nicomoji,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            )
        )
    }, containerColor = containerColor, subTitle = {
        Text(
            text = stringResource(R.string.aflami_description),
            color = Theme.color.textColors.body,
            style = Theme.textStyle.label.small
        )
    }, leadingIcon = {
       // AnimatedPlay()
        IconButton(
            painter = painterResource(R.drawable.home_logo),
            contentDescription = null,
            containerColor = Theme.color.primaryVariant,
            tint = Color.Unspecified,
            paddingValues = PaddingValues(horizontal = 6.dp, vertical = 9.dp),
            withBorder = true,
        )
    }, trailingIcon = {
        IconButton(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            containerColor = Theme.color.primaryVariant,
            tint = Theme.color.textColors.body,
            paddingValues = PaddingValues(8.dp),
            withBorder = true,
            onClick = onSearchClicked
        )
    })
}

@ThemeAndLocalePreviews
@Composable
private fun HomeBarPreview() {
    AflamiTheme {
        Column(
            Modifier
                .background(Theme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeBar(
                containerColor = Theme.color.surface, onSearchClicked = {})
        }
    }
}