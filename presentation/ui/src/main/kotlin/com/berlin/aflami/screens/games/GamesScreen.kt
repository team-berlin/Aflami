package com.berlin.aflami.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.GuessGameDestination
import com.berlin.aflami.screens.games.components.GameCard
import com.berlin.aflami.screens.games.components.LevelDialog
import com.berlin.aflami.screens.games.components.PointScore
import com.berlin.aflami.ui.color.ExtraColors
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.game.GameEffect
import com.berlin.aflami.viewmodel.game.GameInteractionListener
import com.berlin.aflami.viewmodel.game.GameScreenState
import com.berlin.aflami.viewmodel.game.GameType
import com.berlin.aflami.viewmodel.game.GameViewModel
import com.berlin.ui.R

data class GameCardData(
    val gameType: GameType?,
    val title: String,
    val description: String,
    val points: Int,
    val isLocked: Boolean,
    val borderGradient: Brush,
    val shadowColor: Color,
    val circleShadowColor: Color,
    val avatarPainter: Painter,
    val backgroundColor: Color
)

@Composable
fun GamesScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = Theme.navController

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is GameEffect.NavigateToGuessGameScreen -> {
                    navController.navigate(
                        GuessGameDestination(
                            gameType = effect.gameType,
                            numberOfQuestion = effect.numberOfQuestion,
                            numberOfPoint = effect.numberOfPoint,
                            time = effect.time
                        )
                    )
                }

                is GameEffect.ShowError -> {}
            }
        }
    }
    GamesContent(
        gameState = state,
        gameInteractionListener = viewModel
    )
}

@Composable
fun GamesContent(
    gameState: GameScreenState,
    gameInteractionListener: GameInteractionListener,
) {
    val gameCards = listOf(
        GameCardData(
            title = stringResource(R.string.game_guess_character_title),
            description = stringResource(R.string.game_guess_character_desc),
            points = 400,
            isLocked = false,
            borderGradient = ExtraColors.guessMovieByCharacterGradient,
            shadowColor = ExtraColors.shadowGuessMovieByCharacter,
            circleShadowColor = Theme.color.primaryVariant,
            avatarPainter = painterResource(R.drawable.game_clown),
            gameType = GameType.CHARACTER,
            backgroundColor = Theme.color.primaryVariant
        ),
        GameCardData(
            title = stringResource(R.string.game_guess_poster_title),
            description = stringResource(R.string.game_guess_poster_desc),
            points = 400,
            isLocked = false,
            gameType = GameType.POSTER,
            borderGradient = ExtraColors.guessMovieByPosterGradient,
            shadowColor = ExtraColors.shadowGuessMovieByByPoster,
            circleShadowColor = Theme.color.statusColors.blueCard,
            avatarPainter = painterResource(R.drawable.game_poster),
            backgroundColor = Theme.color.statusColors.blueCard
        ),
        GameCardData(
            title = stringResource(R.string.game_release_title),
            description = stringResource(R.string.game_release_desc),
            points = 400,
            gameType = GameType.RELEASE,
            isLocked = gameState.points < 400,
            borderGradient = ExtraColors.guessMovieByReleaseGradient,
            shadowColor = ExtraColors.shadowGuessMovieRelease,
            circleShadowColor = Theme.color.statusColors.navyCard,
            avatarPainter = painterResource(R.drawable.game_release_date),
            backgroundColor = Theme.color.statusColors.navyCard
        ),
        GameCardData(
            title = stringResource(R.string.game_genre_title),
            description = stringResource(R.string.game_genre_desc),
            points = 400,
            gameType = GameType.GENRE,
            isLocked = gameState.points < 400,
            borderGradient = ExtraColors.guessMovieByGenreGradient,
            shadowColor = ExtraColors.shadowGuessMovieByGenre,
            circleShadowColor = Theme.color.statusColors.yellowCard,
            avatarPainter = painterResource(R.drawable.genre),
            backgroundColor = Theme.color.statusColors.yellowCard
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .statusBarsPadding()
            .padding(top = 13.dp)
    ) {
        TopBar(
            title = {
                Text(
                    text = stringResource(R.string.let_s_play),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title
                )
            },
            trailingIcon = { PointScore(gameState.points) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            gameCards.forEachIndexed { index, card ->
                GameCard(
                    title = card.title,
                    description = card.description,
                    points = card.points,
                    isLocked = card.isLocked,
                    onClick = {
                        card.gameType?.let { gameInteractionListener.onSelectGameType(it) }
                        gameInteractionListener.onShowLevelDialog()
                    },
                    borderGradient = card.borderGradient,
                    shadowColor = card.shadowColor,
                    circleShadowColor = card.circleShadowColor,
                    avatarPainter = card.avatarPainter,
                    backgroundColor = card.backgroundColor
                )
            }

            if (gameState.showDialog) {
                LevelDialog(
                    onDismiss = { gameInteractionListener.onDismissLevelDialog() },
                    onLevelSelected = { levelIndex ->
                        gameInteractionListener.onSelectLevel(levelIndex)
                    },
                    onClick = {
                        gameInteractionListener.onGameInfoClicked(
                            gameType = gameState.selectedGameType ?: GameType.GENRE,
                            numberOfQuestion = gameState.selectedLevel?.numberOfQuestions ?: 0,
                            numberOfPoint = gameState.selectedLevel?.points ?: 0,
                            time = gameState.selectedLevel?.time ?: 0
                        )
                    }
                )
            }
        }
    }
}

