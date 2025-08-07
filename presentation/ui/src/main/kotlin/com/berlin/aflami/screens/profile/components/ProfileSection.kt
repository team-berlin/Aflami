package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun ProfileSection(
    userAvatar:String,
    userName:String,
    coverImage: Painter, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(211.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = coverImage,
                    contentDescription = "Cover Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Theme.color.statusColors.profileOverly,
                                    Color.Transparent,
                                    Theme.color.statusColors.profileOverly,
                                    Theme.color.surface,
                                )
                            )
                        )
                )
                Text(
                    text = stringResource(R.string.profile),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 13.dp)
                        .align(Alignment.TopStart)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }

        AvatarSection(
            userAvatar = userAvatar,
            userName = userName,
            userPoints = 100,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 150.dp)
        )


    }
}