package com.berlin.aflami.screens.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.TextField
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.WorldTourHeader
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun WorldTourScreen() {

}


@Preview(showSystemUi = true)
@Composable
fun WorldTourContent() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(Theme.color.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WorldTourHeader(modifier = Modifier.padding(vertical = 16.dp))

        TextField(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.color.surfaceHigh)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            hintText = stringResource(R.string.country_name),
            isEnabled = true,
            maxLines = 1,
            borderColor = Theme.color.stroke,
            onValueChange = {

            }
        )

        CountryTourExploring(
            image = painterResource(R.drawable.world_tour),
            titleId = R.string.country_tour,
            messageId = R.string.start_exploring_the_world_movie_by_enter_your_favorite_country_in_search_bar
        )


    }

}