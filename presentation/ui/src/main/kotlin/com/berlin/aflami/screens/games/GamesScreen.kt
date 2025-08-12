package com.berlin.aflami.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.berlin.ui.R


@Composable
fun GamesScreen(){
    GamesContent()
}

@Composable
fun GamesContent(){
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
       Column (
           modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 16.dp, vertical = 12.dp)
               .verticalScroll(rememberScrollState()),
           verticalArrangement = Arrangement.spacedBy(16.dp)
       ){
           GameCard(
               title = "Guess the Character",
               description = "Can you tell who this character?",
               points = 400,
               isLocked = false,
               onClick = {},
               borderGradient = ExtraColors.guessMovieByCharacterGradient,
               shadowColor = ExtraColors.shadowGuessMovieByCharacter,
               circleShadowColor = Theme.color.primaryVariant,
               diagonalStripePainter = painterResource(id = com.berlin.designsystem.R.drawable.diagonal_stripe),
               avatarPainter = painterResource(R.drawable.avatar),
               backgroundColor = Theme.color.primaryVariant
           )
           GameCard(
               title = "Guess the Movie by Poster",
               description = "Match the poster with the right title!",
               points = 400,
               isLocked = false,
               onClick = {},
               borderGradient = ExtraColors.guessMovieByPosterGradient,
               shadowColor = ExtraColors.shadowGuessMovieByByPoster,
               circleShadowColor = Theme.color.statusColors.blueCard,
               diagonalStripePainter = painterResource(id = com.berlin.designsystem.R.drawable.diagonal_stripe),
               avatarPainter = painterResource(R.drawable.avatar),
               backgroundColor = Theme.color.statusColors.blueCard
           )
           GameCard(
               title = "When Was It Released?",
               description = "Pick the right release year.",
               points = 400,
               isLocked = true,
               onClick = {},
               borderGradient = ExtraColors.guessMovieByReleaseGradient,
               shadowColor = ExtraColors.shadowGuessMovieRelease,
               circleShadowColor = Theme.color.statusColors.navyCard,
               diagonalStripePainter = painterResource(id = com.berlin.designsystem.R.drawable.diagonal_stripe),
               avatarPainter = painterResource(R.drawable.avatar),
               backgroundColor =Theme.color.statusColors.navyCard
           )
           GameCard(
               title = "Which Genre?",
               description = "Which one is the real action movie?",
               points = 400,
               isLocked = true,
               onClick = {},
               borderGradient = ExtraColors.guessMovieByGenreGradient,
               shadowColor = ExtraColors.shadowGuessMovieByGenre,
               circleShadowColor = Theme.color.statusColors.yellowAccent,
               diagonalStripePainter = painterResource(id = com.berlin.designsystem.R.drawable.diagonal_stripe),
               avatarPainter = painterResource(R.drawable.avatar),
               backgroundColor = Theme.color.statusColors.yellowCard
           )

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