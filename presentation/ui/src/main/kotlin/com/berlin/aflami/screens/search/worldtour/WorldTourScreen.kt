package com.berlin.aflami.screens.search.worldtour

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
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.MoviesList
import com.berlin.aflami.screens.search.worldtour.composable.AnimatedCountriesList
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.searchworldtour.WorldTourInteractionListener
import com.berlin.aflami.viewmodel.searchworldtour.WorldTourUiState
import com.berlin.aflami.viewmodel.searchworldtour.WorldTourViewModel
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
private fun WorldTourContent(
    state: WorldTourUiState,
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
                        .clickable { listener::onBackClicked }
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
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            if (state.movies.isEmpty()) {
                CountryTourExploring(
                    modifier = Modifier.padding(top = 143.dp),
                    image = painterResource(R.drawable.world_tour),
                    titleId = R.string.country_tour,
                    messageId = R.string.country_tour_description
                )
            }

            MoviesList(
                movies = state.movies,
                onMovieClick = listener::onMovieClicked
            )

            AnimatedCountriesList(
                visible = state.dropDownExpanded,
                filteredCountries = state.filteredCountries,
                onCountryNameChanged = listener::onCountryNameChanged,
                onCountryClick = listener::onCountrySelected
            )
        }
    }
}