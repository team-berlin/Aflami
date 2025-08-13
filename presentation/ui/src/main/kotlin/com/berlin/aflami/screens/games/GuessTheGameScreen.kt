package com.berlin.aflami.screens.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.berlin.aflami.component.CharacterCard
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.GameResultDestination
import com.berlin.aflami.navigation.NavigationBarDestinations
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.authentication.CirclesBackground
import com.berlin.aflami.screens.games.components.CountdownCircularProgress
import com.berlin.aflami.screens.games.components.Score
import com.berlin.aflami.screens.games.components.SelectionItem
import com.berlin.aflami.screens.onBoarding.Indicator
import com.berlin.aflami.ui.color.ExtraColors.gameBackgroundGradient
import com.berlin.aflami.ui.color.ExtraColors.primaryGredient
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.game.GameType
import com.berlin.aflami.viewmodel.quizgame.QuestionType
import com.berlin.aflami.viewmodel.quizgame.QuizGameEffect
import com.berlin.aflami.viewmodel.quizgame.QuizGameInteractionListener
import com.berlin.aflami.viewmodel.quizgame.QuizGameUiState
import com.berlin.aflami.viewmodel.quizgame.QuizGameViewModel
import com.berlin.ui.R
import kotlinx.coroutines.delay

@Composable
fun GuessTheGameScreen(
    viewModel: QuizGameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController=Theme.navController

    LaunchedEffect(Unit) {
        viewModel.effect.collect{
            when(it){
                QuizGameEffect.CloseGameClicked -> {
                    navController.navigate(
                        NavigationBarDestinations.GamesScreen
                    )
                }

                QuizGameEffect.NavigateToResult -> {
                    navController.navigate(
                       GameResultDestination
                    )
                }
            }
        }
    }

    var showScore by remember { mutableStateOf(false) }

    LaunchedEffect(state.isAnswerCorrect) {
        if (state.isAnswerCorrect == true) {
            showScore = true
            delay(3000)
            showScore = false
        }
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = state.loading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !state.loading
    ) {
        GuessTheGameContent(
            state,
            viewModel,
            showScore
        )
    }


}

@Composable
fun GuessTheGameContent(
    state: QuizGameUiState,
    listener: QuizGameInteractionListener,
    showScore: Boolean
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .background(
                gameBackgroundGradient
            )
            .statusBarsPadding()
            .padding(top = 8.dp)
    ) {
        CirclesBackground()
        Column {
            TopBar(
                modifier = Modifier.zIndex(1f),
                title = {
                    Text(
                        text = state.gameTypeName.type,
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title
                    )
                },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Theme.color.surfaceHigh)
                            .clickable {
                            }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.cancel_01),
                            contentDescription = stringResource(R.string.arrow_back),
                            tint = Theme.color.textColors.title,
                            modifier = Modifier.clickable {
                                listener.closeGameClicked()
                            }

                        )
                    }
                },
                trailingIcon = {
                    CountdownCircularProgress(totalTimePerSecond =state.time){
                        listener.navigateToResult()
                    }
                })
            Indicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                pageNumber = state.currentQuestionIndex,
                pageCount = state.questions.size,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 98.dp, bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if(state.type==QuestionType.Image) {
                CharacterCard(
                    modifier = Modifier.padding(top = 4.dp),
                    imageUrl = state.questions[state.currentQuestionIndex].question,
                    blurAmount = state.imageBlur,
                    onHintClicked = { listener.hintClicked()
//                        if (state.enableHint) {
//                        { }
//                    } else {
//                        {}
                    },
                    showHintBar = true,
                    hintText = "hint? 10 Pts.",
                    hintIcon = com.berlin.designsystem.R.drawable.hint_star,
                )
            }
            else{
                CharacterCard(
                    modifier = Modifier.padding(top = 4.dp),
                    guessedText = state.questions[state.currentQuestionIndex].question,
                    onHintClicked = { listener.hintClicked()
//                        if (state.enableHint) {
//                        { listener.hintClicked() }
//                    } else {
//                        {}
                    },
                    showHintBar = true,
                    hintText = "hint? 10 Pts.",
                    hintIcon = com.berlin.designsystem.R.drawable.hint_star,
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.questions[state.currentQuestionIndex].options.forEach { answer ->
                    SelectionItem(
                        guessName = answer,
                        isSelected = when {
                            state.selectedAnswer.isEmpty() -> null
                            answer == state.selectedAnswer ->
                                answer == state.questions[state.currentQuestionIndex].correctAnswer

                            else -> null
                        },
                        onSelectItem = {
                            if (state.selectedAnswer.isEmpty()) {
                                listener.answerClicked(answer)
                            }
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    if(state.currentQuestionIndex<state.questions.size-1) listener.nextQuestionClicked()
                    else listener.navigateToResult()
                },
                gradientColor = primaryGredient
            ) {
                Text(
                    text = stringResource(R.string.next),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.onPrimary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

        }
        AnimatedVisibility(
            visible = showScore,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Score(
                score ="",
                scoreColor = Theme.color.statusColors.greenAccent,
                backgroundColor = Theme.color.statusColors.greenVariant
            )
        }
    }
}