package com.berlin.aflami.screens.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourInteractionListener
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourUiState
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourViewModel
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun WorldTourScreen(
    viewModel: WorldTourViewModel = koinViewModel()
) {
    val worldTourState by viewModel.uiState.collectAsState()
    var countryName by remember { mutableStateOf("") }

    WorldTourContent(worldTourState,viewModel)

}


@Composable
fun WorldTourContent(
    worldTourState: WorldTourUiState,
    listener: WorldTourInteractionListener,
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface),
    ) {

        TopBar(
            modifier = Modifier.padding(vertical = 8.dp),
            title = {
                Text(
                    text = stringResource(R.string.world_tour),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title,
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Theme.color.surfaceHigh)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "arrow back from world tour search",
                        tint = Theme.color.textColors.title
                    )
                }
            },
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TextField(
                text = worldTourState.countryName,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Theme.color.surfaceHigh),
                hintText = stringResource(R.string.country_name),
                isEnabled = true,
                maxLines = 1,
                borderColor = Theme.color.stroke,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    listener.onSearchClick()
                }),
                onValueChange = listener::onCountryNameChanged
            )
        }

        if (worldTourState.isLoading) {

        }
        if (worldTourState.movies.isEmpty()) {
            CountryTourExploring(
                image = painterResource(R.drawable.world_tour),
                titleId = R.string.country_tour,
                messageId = R.string.start_exploring_the_world_movie_by_enter_your_favorite_country_in_search_bar
            )
        }
//        if (worldTourState.error){
//
//        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp)
        ) {
            items(worldTourState.movies.size) { index ->
                val movie = worldTourState.movies[index]
                MediaCard(
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    mediaImg = movie.poster,
                    title = movie.title,
                    typeOfMedia = movie.description,
                    date = movie.releaseYear,
                    rating = movie.rating.toDouble()
                )
            }
        }
    }


}
