package com.berlin.aflami.screens.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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

    WorldTourContent(worldTourState, viewModel)
}

@Composable
fun WorldTourContent(
    state: WorldTourUiState,
    listener: WorldTourInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
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
                        .clickable {}
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

        val keyboardController = LocalSoftwareKeyboardController.current


        TextField(
            text = state.countryName,
            hintText = stringResource(R.string.country_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp),
            onValueChange = listener::onCountryNameChanged,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }),
            isEnabled = true,
            borderColor = Theme.color.stroke,
            maxLines = 1,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (state.movies.isEmpty()) {
                CountryTourExploring(
                    modifier = Modifier.padding(top = 143.dp),
                    image = painterResource(R.drawable.world_tour),
                    titleId = R.string.country_tour,
                    messageId = R.string.start_exploring_the_world_movie_by_enter_your_favorite_country_in_search_bar
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = state.movies,
                    key = { it.id }
                ) { movie ->
                    MediaCard(
                        modifier = Modifier.height(222.dp),
                        mediaImg = movie.poster,
                        title = movie.title,
                        typeOfMedia = stringResource(R.string.movie),
                        date = movie.releaseYear,
                        rating = movie.rating
                    )
                }
            }

            Column {
                AnimatedVisibility(
                    visible = state.dropDownExpanded
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 16.dp),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .imePadding()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Theme.color.surfaceHigh)
                    ) {
                        item {
                            Text(
                                text = stringResource(R.string.countries),
                                style = Theme.textStyle.label.medium,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        itemsIndexed(
                            items = state.filteredCountries.toList(),
                            key = { _, countryWithCode -> countryWithCode.second }
                        ) { index, it ->
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        listener.onCountrySelected(it.first)
                                        listener.onSearchClick()
                                    }
                                    .padding(start = 16.dp)
                                    .animateItem(fadeInSpec = null, fadeOutSpec = null)
                                    .fillMaxWidth()
                                    .padding(horizontal = 0.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    text = it.first,
                                    style = Theme.textStyle.body.medium,
                                    color = Theme.color.secondary
                                )
                            }
                            if (index != state.filteredCountries.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .background(Theme.color.stroke)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}