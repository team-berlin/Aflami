package com.berlin.aflami.screens.search.actor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.TextField
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.MoviesList
import com.berlin.aflami.screens.search.components.SearchTopBar
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search_actor.SearchByActorInteractionListener
import com.berlin.aflami.viewmodel.search_actor.SearchByActorScreenUiState
import com.berlin.aflami.viewmodel.search_actor.SearchByActorViewModel
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchByActorNameScreen(
    viewModel: SearchByActorViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchByActorNameContent(uiState, viewModel)
}

@Composable
private fun SearchByActorNameContent(
    state: SearchByActorScreenUiState,
    listener: SearchByActorInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Theme.color.surface),
    ) {
        SearchTopBar(
            title = stringResource(R.string.world_tour),
            onBackClick = listener::onBackClick
        )

        SearchTextField(
            countryName = state.actorName,
            hint = stringResource(R.string.actor_name),
            onActorNameChanged = listener::onActorNameChanged,
            onSearchClick = listener::onSearchClick
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (state.movies.isEmpty()) {
                CountryTourExploring(
                    modifier = Modifier.padding(top = 143.dp),
                    image = painterResource(R.drawable.find_by_actor),
                    titleId = R.string.find_by_actor,
                    messageId = R.string.start_exploring_your_favorite_actor_s_movies_and_enjoy_it
                )
            }

            MoviesList(
                movies = state.movies,
                onMovieClick = listener::onClickMovie
            )
        }
    }
}

@Composable
private fun SearchTextField(
    countryName: String,
    hint: String = "",
    onActorNameChanged: (CharSequence) -> Unit,
    onSearchClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        text = countryName,
        hintText = hint,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp),
        onValueChange = onActorNameChanged,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
                keyboardController?.hide()
            }
        ),
        isEnabled = true,
        borderColor = Theme.color.stroke,
        maxLines = 1,
    )
}