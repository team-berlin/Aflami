package com.berlin.aflami.screens.search.actor

import android.annotation.SuppressLint
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.MediaGridList
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.searchactor.SearchByActorEffect
import com.berlin.aflami.viewmodel.searchactor.SearchByActorInteractionListener
import com.berlin.aflami.viewmodel.searchactor.SearchByActorScreenUiState
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.ui.R
import com.example.navigation.Destination
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchByActorNameScreen(
    navController: NavController, viewModel: SearchByActorViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is SearchByActorEffect.NavigatedBack -> {
                    navController.popBackStack()
                }

                is SearchByActorEffect.NavigatedToMediaDetailsScreen -> {
                    navController.navigate(
                        Destination.MediaDetailsScreen.route(
                            it.movieId,
                            "MOVIE"
                        )
                    )
                }
            }
        }
    }
    SearchByActorNameContent(
        state = uiState,
        listener = viewModel,
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun SearchByActorNameContent(
    state: SearchByActorScreenUiState,
    listener: SearchByActorInteractionListener,
) {
    Column {
        TopBar(modifier = Modifier.padding(vertical = 8.dp), title = {
            Text(
                text = stringResource(R.string.find_by_actor),
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.title,
            )
        }, leadingIcon = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Theme.color.surfaceHigh)
                    .clickable {
                        listener.onBackClicked()
                    }
                    .padding(10.dp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.arrow_back),
                    tint = Theme.color.textColors.title
                )
            }
        })
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            text = state.query,
            hintText = stringResource(R.string.actor_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp),
            onValueChange = listener::onActorNameChanged,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { keyboardController?.hide() }),
            isEnabled = true,
            borderColor = Theme.color.stroke,
            maxLines = 1,
        )

        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            val pagedMovies = state.movies.collectAsLazyPagingItems()
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                pagedMovies.itemCount == 0 && state.query.isBlank() -> {
                    CountryTourExploring(
                        modifier = Modifier.fillMaxSize(),
                        image = painterResource(R.drawable.find_by_actor),
                        titleId = R.string.find_by_actor,
                        messageId = R.string.find_by_actor_quotation
                    )
                }

                pagedMovies.itemCount == 0 && state.query.isNotBlank() -> {
                    CountryTourExploring(
                        modifier = Modifier.fillMaxSize(),
                        image = painterResource(R.drawable.no_search_result),
                        titleId = R.string.no_search_result,
                        messageId = R.string.please_try_with_another_keyword
                    )
                }

                else -> {
                    MediaGridList(
                        media = pagedMovies,
                        onMovieClick = listener::onMovieClicked,
                    )
                }
            }
        }
    }
}

