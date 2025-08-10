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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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


@Composable
fun GuessTheGameScreen() {

}
@Preview(showSystemUi = true)
@Composable
private fun GuessTheGameContent() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .background(
                gameBackgroundGradient
            )
    ) {
        CirclesBackground()

        TopBar(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .zIndex(1f),
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
                    timePerSecond = 45
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 72.dp, bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Indicator(
                pageNumber = 2,
                pageCount = 6,
            )
            CharacterCard(
                modifier = Modifier.padding(top = 4.dp),
//                guessedText = "The green mile",
                imageRes = painterResource(id = R.drawable.profile_avatar),
                blurAmount = 20f,
                onHintClicked = {},
                showHintBar = true,
                hintText = "hint? 10 Pts.",
                hintIcon = com.berlin.designsystem.R.drawable.hint_star,
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                repeat(4) {
                    SelectionItem(
                        onSelectItem = {},
                        guessName = "The green mile",
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
                    text = "Next",
                    style = Theme.textStyle.label.large,
                    color = Theme.color.textColors.onPrimary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
    }
}