package com.berlin.aflami.screens.games.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.extension.dropShadow
import com.berlin.aflami.ui.color.ExtraColors
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.game.GameType
import com.berlin.ui.R

@Composable
fun GameCard(
    title: String,
    description: String,
    points: Int,
    isLocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderGradient: Brush,
    shadowColor: Color,
    circleShadowColor: Color,
    avatarPainter: Painter,
    gameType: GameType,
) {
    val layoutDirection = LocalLayoutDirection.current
    var targetHeight by remember { mutableStateOf(0.dp) }
    val animatedHeight by animateDpAsState(targetValue = targetHeight)
    val density = LocalDensity.current

    Box (
        Modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(10.dp)
                .dropShadow(
                    shape = RectangleShape,
                    color = circleShadowColor,
                    blur = 8.dp,
                )
               // .background(circleShadowColor)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onSizeChanged { size ->
                    targetHeight = with(density) { size.height.toDp() }
                }
                .clip(RoundedCornerShape(16))
                .background(backgroundColor)
                .border(
                    1.dp,
                    borderGradient,
                    RoundedCornerShape(16),
                )

        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLocked) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Theme.color.surfaceHigh)
                            .padding(vertical = 4.dp)
                            .zIndex(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.pts_to_unlock, points),
                            style = Theme.textStyle.label.small,
                            color = Theme.color.statusColors.yellowAccent,
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dropShadow(
                            color = shadowColor,
                            blur = 12.dp,
                            offsetY = 4.dp,
                            offsetX = 0.dp,
                            spread = 12.dp,
                            shape = CircleShape,
                            alpha = 0.0f
                        )
                ) {


                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = title,
                            style = Theme.textStyle.title.small,
                            color = Theme.color.textColors.title
                        )
                        Text(
                            text = description,
                            style = Theme.textStyle.body.small,
                            color = Theme.color.textColors.body,
                            maxLines = 2,
                            modifier = Modifier.fillMaxWidth(0.5f)

                        )

                        if (!isLocked) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100))
                                    .background(Theme.color.onPrimaryButton)
                                    .border(
                                        0.5.dp,
                                        ExtraColors.borderActionGameGard,
                                        RoundedCornerShape(100),
                                    )
                                    .clickable { onClick() }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.play_now),
                                    style = Theme.textStyle.label.small,
                                    color = Theme.color.textColors.title
                                )
                                Icon(
                                    painter = painterResource(R.drawable.play_game),
                                    tint = Theme.color.textColors.title,
                                    contentDescription = "Star",
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(Theme.color.onPrimaryButton)
                                    .border(
                                        0.5.dp,
                                        ExtraColors.borderActionGameGard,
                                        RoundedCornerShape(100),
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.lock),
                                    tint = Theme.color.textColors.title,
                                    contentDescription = "locked game",
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }

                }
            }
            Image(
                painter = painterResource(R.drawable.spot_light),
                contentDescription = "",
                modifier = Modifier
                    .height(animatedHeight)
                    .align(Alignment.TopEnd)
                    .graphicsLayer {
                        if (layoutDirection == LayoutDirection.Rtl) {
                            scaleX = -1f
                        }
                    }
            )
            Image(
                painter = painterResource(R.drawable.spot_light_small),
                contentDescription = "",
                modifier = Modifier
                    .height(animatedHeight)
                    .align(Alignment.BottomEnd)
                    .graphicsLayer {
                        if (layoutDirection == LayoutDirection.Rtl) {
                            scaleX = -1f
                        }
                    }
            )
            when (gameType) {
                GameType.CHARACTER ->
                    Image(
                        modifier = Modifier
                            .height(88.dp)
                            .align(Alignment.BottomEnd)
                            .graphicsLayer {
                                if (layoutDirection == LayoutDirection.Rtl) {
                                    scaleX = -1f
                                }
                            }
                               ,

                        painter = avatarPainter,
                        contentDescription = "Guess the Character game"
                    )

                GameType.POSTER -> {
                    Image(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .align(Alignment.BottomEnd)
                            .graphicsLayer {
                                if (layoutDirection == LayoutDirection.Rtl) {
                                    scaleX = -1f
                                }
                            }
                            .height(110.dp),
                        painter = avatarPainter,
                        contentDescription = "guess by poster game",
                    )
                }

                GameType.RELEASE -> Image(
                    painter = avatarPainter,
                    contentDescription = "guess the release date game",
                    modifier = Modifier
                        .graphicsLayer {
                            if (layoutDirection == LayoutDirection.Rtl) {
                                scaleX = -1f
                            }
                        }
                        .size(height = 88.dp, width = 95.dp)
                        .align(Alignment.BottomEnd)
                )

                GameType.GENRE -> Image(
                    painter = avatarPainter,
                    contentDescription = "guess the movie genre",
                    modifier = Modifier
                        .graphicsLayer {
                            if (layoutDirection == LayoutDirection.Rtl) {
                                scaleX = -1f
                            }
                        }
                        .size(height = 88.dp, width = 92.dp)
                        .align(Alignment.BottomEnd)
                )
            }
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .align(Alignment.TopEnd)
                        .dropShadow(
                            blur = 16.dp,
                            alpha = .32f,
                            color = circleShadowColor,
                            shape = CircleShape
                        )
                )
        }

    }
}


@Composable
@ThemeAndLocalePreviews
fun GameCardPreview() {
    AflamiTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GameCard(
                title = "Guess the Character",
                description = "Can you tell who this character?",
                points = 400,
                isLocked = false,
                onClick = {},
                borderGradient = ExtraColors.guessMovieByGenreGradient,
                shadowColor = ExtraColors.shadowGuessMovieByGenre,
                circleShadowColor = Theme.color.statusColors.navyCard,
                avatarPainter = painterResource(R.drawable.game_clown),
                backgroundColor = Theme.color.statusColors.navyCard,
                gameType = GameType.CHARACTER
            )
            GameCard(
                title = "Guess the Character",
                description = "Can you tell who this character?",
                points = 400,
                isLocked = false,
                onClick = {},
                borderGradient = ExtraColors.guessMovieByGenreGradient,
                shadowColor = ExtraColors.shadowGuessMovieByGenre,
                circleShadowColor = Theme.color.statusColors.navyCard,
                avatarPainter = painterResource(R.drawable.poster_game),
                backgroundColor = Theme.color.statusColors.navyCard,
                gameType = GameType.POSTER
            )
            GameCard(
                title = "Guess the Character",
                description = "Can you tell who this character?",
                points = 400,
                isLocked = true,
                onClick = {},
                borderGradient = ExtraColors.guessMovieByGenreGradient,
                shadowColor = ExtraColors.shadowGuessMovieByGenre,
                circleShadowColor = Theme.color.statusColors.navyCard,
                avatarPainter = painterResource(R.drawable.game_release_date),
                backgroundColor = Theme.color.statusColors.navyCard,
                gameType = GameType.RELEASE

            )
            GameCard(
                title = "Guess the Character",
                description = "Can you tell who this character?",
                points = 400,
                isLocked = true,
                onClick = {},
                borderGradient = ExtraColors.guessMovieByGenreGradient,
                shadowColor = ExtraColors.shadowGuessMovieByGenre,
                circleShadowColor = Theme.color.statusColors.navyCard,
                avatarPainter = painterResource(R.drawable.genre),
                backgroundColor = Theme.color.statusColors.navyCard,
                gameType = GameType.GENRE
            )
        }

    }
}