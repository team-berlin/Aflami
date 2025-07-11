package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.TextField
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun CountryTourExploring(
    modifier: Modifier=Modifier,
    image:Painter,
    titleId:Int,
    messageId:Int
){

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = image,
            contentDescription = "Country tour exploring",
            modifier = Modifier.height(80.dp),
            contentScale = ContentScale.FillHeight
        )
        Text(
            text = stringResource(titleId),
            style = Theme.textStyle.title.medium,
            color = Theme.color.textColors.title,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Text(
            text = stringResource(messageId),
            style = Theme.textStyle.body.small,
            color = Theme.color.textColors.body,
            textAlign = TextAlign.Center
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun CountryTourExploringPreview() {
    CountryTourExploring(
        modifier = Modifier,
        painterResource(R.drawable.world_tour),
        R.string.world_tour,
        R.string.start_exploring_the_world_movie_by_enter_your_favorite_country_in_search_bar

    )

}

@Preview(showBackground = true)
@Composable
private fun NoResultPreview() {
    CountryTourExploring(
        modifier = Modifier,
        painterResource(R.drawable.no_search_result),
        R.string.no_search_result,
        R.string.please_try_with_another_keyword

    )

}