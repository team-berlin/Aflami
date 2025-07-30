package com.berlin.aflami.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import androidx.navigation.NavController
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.ContinueWatchingScreen
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.screens.search.components.MediaGridList
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaEffect
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaInteractionListener
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaUiState
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaViewModel
import com.berlin.ui.R
import com.example.navigation.Destination
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContinueWatchingScreen(
    viewModel: ContinueWatchingMediaViewModel = koinViewModel(),
) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            onReceiveEffect(navController = navController, effect = newEffect)
        }
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = state.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !state.isLoading
    ) {
        WatchedMediaContent(
            state = state, listener = viewModel
        )
    }
}

private fun onReceiveEffect(navController: NavController, effect: ContinueWatchingMediaEffect) {
    when (effect) {
        is ContinueWatchingMediaEffect.NavigateToDetails -> {
            navController.navigate(
                ContinueWatchingScreen
            )
        }

        is ContinueWatchingMediaEffect.OnBackClicked -> {
            navController.popBackStack()
        }
    }
}

@Composable
fun WatchedMediaContent(
    state: ContinueWatchingMediaUiState, listener: ContinueWatchingMediaInteractionListener,
) {

    Column(modifier = Modifier.fillMaxSize().background(Theme.color.surface)) {
        TopBar(modifier = Modifier.padding(vertical = 8.dp), title = {
            Text(
                text = stringResource(R.string.continue_watching),
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

        val pagedMovies = state.continueWatchingItems.collectAsLazyPagingItems()

        when {
            state.isLoading -> {
                Loading()
            }

            else -> {
                MediaGridList(
                    media = pagedMovies,
                    onMovieClick = listener::onMediaCardClicked,
                )
            }
        }

    }

}
