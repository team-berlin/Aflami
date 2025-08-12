package com.berlin.aflami.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.games.components.GameCard
import com.berlin.aflami.screens.games.components.PointScore
import com.berlin.aflami.ui.color.ExtraColors
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.quizgame.GameType
import com.berlin.ui.R

data class GameCardData(
    val gameType:GameType?=null,
    val title: String,
    val description: String,
    val points: Int,
    val isLocked: Boolean,
    val borderGradient: androidx.compose.ui.graphics.Brush,
    val shadowColor: androidx.compose.ui.graphics.Color,
    val circleShadowColor: androidx.compose.ui.graphics.Color,
    val avatarPainter: androidx.compose.ui.graphics.painter.Painter,
    val backgroundColor: androidx.compose.ui.graphics.Color
)

@Composable
fun GamesScreen() {
    GamesContent()
}

@Composable
fun GamesContent() {
    val gameCards = listOf(
        GameCardData(
            title = "Guess the Character",
            description = "Can you tell who this character?",
            points = 400,
            isLocked = false,
            borderGradient = ExtraColors.guessMovieByCharacterGradient,
            shadowColor = ExtraColors.shadowGuessMovieByCharacter,
            circleShadowColor = Theme.color.primaryVariant,
            avatarPainter = painterResource(R.drawable.avatar),
            backgroundColor = Theme.color.primaryVariant
        ),
        GameCardData(
            title = "Guess the Movie by Poster",
            description = "Match the poster with the right title!",
            points = 400,
            isLocked = false,
            borderGradient = ExtraColors.guessMovieByPosterGradient,
            shadowColor = ExtraColors.shadowGuessMovieByByPoster,
            circleShadowColor = Theme.color.statusColors.blueCard,
            avatarPainter = painterResource(R.drawable.avatar),
            backgroundColor = Theme.color.statusColors.blueCard
        ),
        GameCardData(
            title = "When Was It Released?",
            description = "Pick the right release year.",
            points = 400,
            isLocked = true,
            borderGradient = ExtraColors.guessMovieByReleaseGradient,
            shadowColor = ExtraColors.shadowGuessMovieRelease,
            circleShadowColor = Theme.color.statusColors.navyCard,
            avatarPainter = painterResource(R.drawable.avatar),
            backgroundColor = Theme.color.statusColors.navyCard
        ),
        GameCardData(
            title = "Which Genre?",
            description = "Which one is the real action movie?",
            points = 400,
            isLocked = true,
            borderGradient = ExtraColors.guessMovieByGenreGradient,
            shadowColor = ExtraColors.shadowGuessMovieByGenre,
            circleShadowColor = Theme.color.statusColors.yellowCard,
            avatarPainter = painterResource(R.drawable.avatar),
            backgroundColor = Theme.color.statusColors.yellowCard
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        TopBar(
            title = {
                Text(
                    text = "Let’s play",
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title
                )
            },
            trailingIcon = { PointScore(300) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            gameCards.forEach { card ->
                GameCard(
                    title = card.title,
                    description = card.description,
                    points = card.points,
                    isLocked = card.isLocked,
                    onClick = {},
                    borderGradient = card.borderGradient,
                    shadowColor = card.shadowColor,
                    circleShadowColor = card.circleShadowColor,
                    avatarPainter = card.avatarPainter,
                    backgroundColor = card.backgroundColor
                )
            }
        }
    }
}

@Composable
@ThemeAndLocalePreviews
fun GamesContentPreview() {
    AflamiTheme {
        GamesContent()
    }
}