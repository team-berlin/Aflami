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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.berlin.aflami.component.CharacterCard
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.authentication.CirclesBackground
import com.berlin.aflami.screens.games.components.CountdownCircularProgress
import com.berlin.aflami.screens.games.components.SelectionItem
import com.berlin.aflami.screens.onBoarding.Indicator
import com.berlin.aflami.ui.color.ExtraColors.gameBackgroundGradient
import com.berlin.aflami.ui.color.ExtraColors.primaryGredient
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.quizgame.QuizGameInteractionListener
import com.berlin.aflami.viewmodel.quizgame.QuizGameUiState
import com.berlin.aflami.viewmodel.quizgame.QuizGameViewModel
import com.berlin.ui.R

@Composable
fun GuessTheGameScreen(
    viewModel: QuizGameViewModel = hiltViewModel()
) {
    val questionUiState by viewModel.state.collectAsState()

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = questionUiState.loading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !questionUiState.loading
    ) {
        GuessTheGameContent(
            questionUiState,
            viewModel
        )
    }


}

@Composable
fun GuessTheGameContent(
    state:QuizGameUiState,
    listener:QuizGameInteractionListener
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
            Column(
            ) {
                TopBar(
                    modifier = Modifier.zIndex(1f),
                    title = {
                        Text(
                            text = stringResource(R.string.guess_the_character),
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
                                tint = Theme.color.textColors.title
                            )
                        }
                    },
                    trailingIcon = {
                        CountdownCircularProgress(
//                    totalTimePerSecond =currentQuestion.time,
                            totalTimePerSecond = 45,

                            )
                    })
                Indicator(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 16.dp),
                    pageNumber =state.currentQuestionIndex,
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
                CharacterCard(
                    modifier = Modifier.padding(top = 4.dp),
//                guessedText = "The green mile",
                    imageUrl = state.questions[state.currentQuestionIndex].question,
                    blurAmount = 8f,
                    onHintClicked = {},
                    showHintBar = true,
                    hintText = "hint? 10 Pts.",
                    hintIcon = com.berlin.designsystem.R.drawable.hint_star,
                )
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
                                if(state.selectedAnswer.isEmpty()) {
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
                        listener.nextQuestionClicked()
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
        }
}