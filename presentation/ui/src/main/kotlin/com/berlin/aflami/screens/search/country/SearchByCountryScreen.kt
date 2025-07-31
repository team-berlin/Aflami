package com.berlin.aflami.screens.search.country

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.MediaDetailsDestination
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.MoviesList
import com.berlin.aflami.screens.search.country.composable.AnimatedCountriesList
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryEffect
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryInteractionListener
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryScreenUiState
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchByCountryScreen(
    viewModel: SearchByCountryViewModel = koinViewModel(),
) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsState()
    SearchByCountryContent(
        navController = navController,
        state = state,
        listener = viewModel
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SearchByCountryEffect.NavigatedBack -> navController.popBackStack()
                is SearchByCountryEffect.NavigatedToMovieDetailsScreen -> {
                    navController.navigate(
                        "mediaDetailsScreen/${effect.movieId}/${"MOVIE"}"
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchByCountryContent(
    state: SearchByCountryScreenUiState,
    listener: SearchByCountryInteractionListener,
    navController: NavController,
) {
    Column {
        TopBar(
            modifier = Modifier.padding(vertical = 8.dp),
            title = {
                Text(
                    text = stringResource(R.string.country_tour),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title,
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Theme.color.surfaceHigh)
                        .clickable { listener.onBackClicked() }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.arrow_back),
                        tint = Theme.color.textColors.title
                    )
                }
            }
        )

        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            text = state.query,
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
                .fillMaxWidth(),
        ) {
            val movies = state.movies.collectAsLazyPagingItems()

            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.query.text.isBlank() && movies.itemCount == 0 -> {
                    CountryTourExploring(
                        modifier = Modifier.fillMaxSize(),
                        image = painterResource(R.drawable.world_tour),
                        titleId = R.string.country_tour,
                        messageId = R.string.country_tour_description
                    )
                }

                state.isCountrySelected && movies.itemCount == 0
                        && movies.loadState.refresh is LoadState.NotLoading -> {
                    CountryTourExploring(
                        modifier = Modifier.fillMaxSize(),
                        image = painterResource(R.drawable.no_search_result),
                        titleId = R.string.no_search_result,
                        messageId = R.string.please_try_with_another_keyword
                    )
                }

                else -> {
                    MoviesList(
                        movies = movies,
                        onMovieClick = { movieId, mediaType ->
                            navController.navigate(
                                MediaDetailsDestination(
                                    movieId,
                                    MediaType.valueOf("MOVIE"),
                                )
                            )
                        }
                    )
                }
            }

            AnimatedCountriesList(
                visible = state.dropDownExpanded && state.filteredCountries.isNotEmpty(),
                filteredCountries = state.filteredCountries,
                onCountryNameChanged = listener::onCountryNameChanged,
                onCountryClick = listener::onCountryClicked
            )
        }
    }
}