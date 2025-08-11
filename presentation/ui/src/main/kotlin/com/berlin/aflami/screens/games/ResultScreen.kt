package com.berlin.aflami.screens.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.berlin.aflami.component.buttons.PrimaryButton
import com.berlin.aflami.component.buttons.SecondaryButton
import com.berlin.aflami.screens.authentication.CirclesBackground
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun ResultScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .background(
                brush = Brush.verticalGradient(
                    colors = Theme.color.gradientColors.streakGradient
                )
            )
            .navigationBarsPadding()
            .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        CirclesBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            ResultHeader()

            ResultBox()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val secUnit = stringResource(R.string.point_count_sec)
                val pteUnit = stringResource(R.string.points_unit_pte)
                ResultPointesBox(
                    modifier.weight(1f),
                    title = stringResource(R.string.points_achieved),
                    pointCount = "110 $pteUnit.",
                    image = painterResource(R.drawable.my_rating),
                )

                ResultPointesBox(
                    modifier.weight(1f),
                    title = stringResource(R.string.total_time),
                    pointCount = "110 $secUnit",
                    image = painterResource(R.drawable.watch_history),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                {},
                shape = RoundedCornerShape(16.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            ) {
                Text(
                    text = stringResource(R.string.back_to_menu),
                    style = Theme.textStyle.label.large,
                )
            }

            SecondaryButton(
                {}, shape = RoundedCornerShape(16.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                border = null
            ) {
                Text(
                    text = stringResource(R.string.play_again),
                    style = Theme.textStyle.label.large
                )
            }
        }
    }
}

@Composable
private fun ResultHeader(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .background(
                    Theme.color.surfaceHigh,
                    shape = RoundedCornerShape(12.dp)
                )
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Theme.color.textColors.title
            )
        }
        Text(
            text = stringResource(R.string.guess_character),
            style = Theme.textStyle.title.large,
            color = Theme.color.textColors.title,
        )
    }
}

@Composable
private fun ResultBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .background(
                Theme.color.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                1.dp, Theme.color.stroke,
                RoundedCornerShape(24.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    Theme.color.primaryVariant,
                    shape = RoundedCornerShape(20.dp)
                )
                .paint(
                    painter = painterResource(R.drawable.rays),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        Theme.color.primary
                    )
                )
                .clip(RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.prize),
                    contentDescription = "prize",
                    modifier = Modifier.size(130.dp)
                )
                Text(
                    text = stringResource(R.string.game_finished),
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium
                )
            }
        }
    }
}

@Composable
private fun ResultPointesBox(
    modifier: Modifier = Modifier,
    title: String,
    pointCount: String,
    image: Painter,
) {
    Box(
        modifier = modifier
            .background(
                Theme.color.surface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .height(92.dp)
                    .background(
                        Theme.color.surface,
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Theme.color.surface,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .paint(
                            painter = painterResource(R.drawable.small_rays),
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(
                                Theme.color.primary.copy(
                                    0.25f
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Theme.color.stroke,
                            RoundedCornerShape(16.dp)
                        )
                )
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(1f)
                        .align(Alignment.TopCenter)
                        .offset(y = (-18).dp),
                    contentScale = ContentScale.Inside
                )

            }
            Text(
                title,
                color = Theme.color.textColors.hint,
                style = Theme.textStyle.label.medium,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                "$pointCount",
                color = Theme.color.textColors.title,
                style = Theme.textStyle.headline.medium.copy(
                    lineHeight = 36.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ResultScreenPreview() {
    AflamiTheme {
        ResultScreen()
    }
}