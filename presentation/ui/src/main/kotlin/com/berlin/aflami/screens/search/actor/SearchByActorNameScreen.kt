package com.berlin.aflami.screens.search.actor

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.MediaGridList
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.base.NetworkErrorState
import com.berlin.aflami.viewmodel.searchactor.SearchByActorInteractionListener
import com.berlin.aflami.viewmodel.searchactor.SearchByActorScreenEffect
import com.berlin.aflami.viewmodel.searchactor.SearchByActorScreenState
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchByActorNameScreen(
    viewModel: SearchByActorViewModel = hiltViewModel(),
) {
    val navController = Theme.navController
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            onReceiveSearchByActorEffect(
                navController = navController,
                searchByActorScreenEffect = effect
            )
        }
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = !uiState.isLoading
    ) {

        SearchByActorNameContent(
            state = uiState,
            listener = viewModel,
        )
    }

}

private fun onReceiveSearchByActorEffect(
    navController: NavController,
    searchByActorScreenEffect: SearchByActorScreenEffect
) {
    when (searchByActorScreenEffect) {
        is SearchByActorScreenEffect.NavigatedBack -> {
            navController.popBackStack()
        }

        is SearchByActorScreenEffect.NavigatedToMediaDetailsScreen -> {
            navController.navigate(
                MovieDetailsDestination(
                    searchByActorScreenEffect.movieId,
                )
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun SearchByActorNameContent(
    state: SearchByActorScreenState,
    listener: SearchByActorInteractionListener,
) {

    val searchResult: LazyPagingItems<MediaUiState> =
        state.mediaPagingDataFlow.collectAsLazyPagingItems()

    val isPagingLoading = searchResult.loadState.refresh is LoadState.Loading
    val pagingError = (searchResult.loadState.refresh as? LoadState.Error)
    val isLoading = state.isLoading || isPagingLoading
    val hasError = state.errorUiState is NetworkErrorState || pagingError != null
    val isSearchEmpty = state.actorName.text.isBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        TopBar(modifier = Modifier
            .statusBarsPadding()
            .padding(vertical = 8.dp), title = {
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
            text = state.actorName,
            hintText = stringResource(R.string.actor_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            onValueChange = { listener.onActorNameChanged(it) },
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
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = Triple(isLoading, hasError, searchResult.itemCount),
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
            ) { (loading, error, count) ->
                when {
                    loading -> {
                        if (isSearchEmpty) {
                            InitContent()
                        } else {
                            CircularProgressIndicator(
                                text = stringResource(R.string.loading)
                            )
                        }
                    }

                    error -> {
                        NoInternetConnectionPlaceholder(
                            onClick = {
                                searchResult.retry()
                            }
                        )
                    }

                    count == 0 && !isSearchEmpty -> {
                        ErrorContent()
                    }

                    else -> {
                        if (isSearchEmpty) {
                            InitContent()
                        } else {
                            MediaGridList(
                                media = searchResult,
                                onMovieClick = listener::onMediaCardClicked,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorContent() {
    CountryTourExploring(
        modifier = Modifier.fillMaxSize(),
        image = painterResource(R.drawable.no_search_result),
        titleId = R.string.no_search_result,
        messageId = R.string.please_try_with_another_keyword
    )
}

@Composable
private fun InitContent() {
    CountryTourExploring(
        modifier = Modifier.fillMaxSize(),
        image = painterResource(R.drawable.find_by_actor),
        titleId = R.string.find_by_actor,
        messageId = R.string.find_by_actor_quotation
    )
}

