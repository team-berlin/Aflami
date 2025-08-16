package com.berlin.aflami.screens.games.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    gameType: GameType
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16))
            .background(backgroundColor)
            .border(
                1.dp,
                borderGradient,
                RoundedCornerShape(16),
            )
            .dropShadow(
                color = shadowColor,
                blur = 12.dp,
                offsetY = 4.dp,
                offsetX = 0.dp,
                spread = 0.dp,
                shape =  RoundedCornerShape(16.dp),
                alpha = 0.0f
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
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .padding(bottom = 30.dp)
                        .align(Alignment.TopEnd)
                        .dropShadow(
                            blur = 32.dp,
                            color = circleShadowColor,
                            shape = CircleShape
                        )
                )

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
                        modifier = Modifier.width(175.dp)
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
                                contentDescription = "Star",
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
                Column (
                    modifier =  Modifier.height(140.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        Image(
                            painter = painterResource(R.drawable.spot_light),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.TopEnd),

                            )
                        Image(
                            painter = painterResource(R.drawable.spot_light_small),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .align(Alignment.BottomEnd)
                        )
                        when (gameType) {
                            GameType.CHARACTER ->
                                Image(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd),
                                    painter = avatarPainter,
                                    contentScale = ContentScale.FillHeight,
                                    contentDescription = ""
                                )

                            GameType.POSTER -> Image(
                                modifier = Modifier
                                    .padding(end = 25.dp, top = 20.dp)
                                    .align(Alignment.BottomEnd)
                                    .offset(y = 40.dp)
                                    .scale(scaleY = 1.5f, scaleX = 1.5f)
                                    .clip(RoundedCornerShape(12.dp)),
                                painter = avatarPainter,
                                contentDescription = "",
                            )


                            GameType.RELEASE -> Image(
                                painter = avatarPainter,
                                contentDescription = "Character",
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.BottomEnd)
                            )

                            GameType.GENRE -> Image(
                                painter = avatarPainter,
                                contentDescription = "Character",
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.BottomEnd)
                            )

                        }

                    }
                }

            }
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
                avatarPainter = painterResource(R.drawable.game_poster),
                backgroundColor = Theme.color.statusColors.navyCard,
                        gameType =GameType.POSTER
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