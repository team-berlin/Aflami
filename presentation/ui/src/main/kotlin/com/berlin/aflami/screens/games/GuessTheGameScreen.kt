package com.berlin.aflami.screens.games

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.berlin.aflami.component.CharacterCard
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.authentication.CirclesBackground
import com.berlin.aflami.screens.games.components.CountdownCircularProgress
import com.berlin.aflami.screens.games.components.SelectionItem
import com.berlin.aflami.screens.onBoarding.Indicator
import com.berlin.aflami.ui.color.ExtraColors.gameBackgroundGradient
import com.berlin.aflami.ui.color.ExtraColors.primaryGredient
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

data class Question(
    val imageRes: Painter,
    val questionCount:Int,
    val questionAnswers: Pair<Int,List<String>>,
    val selectAnswer: String?=null,
    val correctAnswer:String,
    val timer: Int
)

@Composable
fun GuessTheGameScreen() {

    GuessTheGameContent(
        questionUiState = Question(
            imageRes = painterResource(R.drawable.profile_avatar),
            questionCount = 5,
            questionAnswers = Pair(5,listOf("The Green Mile", "Avatar", "Inception", "Titanic")),
            selectAnswer = "The Green Mile",
            correctAnswer = "The Green ",
            timer = 10
        )
    )

}

@Composable
fun GuessTheGameContent(
    questionUiState: Question
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
            TopBar(modifier = Modifier.zIndex(1f),
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
                    totalTimePerSecond = questionUiState.timer,
                )
            })
            Indicator(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 16.dp),
                pageNumber = 5,
                pageCount = 25,
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
                imageRes = questionUiState.imageRes,
                blurAmount = 20f,
                onHintClicked = {},
                showHintBar = true,
                hintText = "hint? 10 Pts.",
                hintIcon = com.berlin.designsystem.R.drawable.hint_star,
            )
            var showResult by remember { mutableStateOf(false) }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                questionUiState.questionAnswers.second.forEach{answer->
                    SelectionItem(
                        guessName = answer,
                        isSelected = when {
                            !showResult -> {
                                if (answer == questionUiState.selectAnswer) true else null
                            }

                            else -> {
                                if (answer == questionUiState.correctAnswer) true else false
                            }
                        },
                        onSelectItem = {
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {},
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