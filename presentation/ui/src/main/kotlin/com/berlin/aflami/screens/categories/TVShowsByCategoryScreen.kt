package com.berlin.aflami.screens.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.screens.search.search.Chips
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryInteractionListener
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryScreenEffect
import com.berlin.aflami.viewmodel.categories.tvshow.TVShowByCategoryScreenViewModel
import com.berlin.aflami.viewmodel.categories.tvshow.TVShowByCategoryUiState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.ui.R


@Composable
fun TVShowByCategoryScreen(
    viewModel: TVShowByCategoryScreenViewModel = hiltViewModel(),
) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            categoriesReceiveEffect(navController = navController, effect = newEffect)
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
    val tvShows = state.tvShowsPagingDataFlow.collectAsLazyPagingItems()
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !state.isLoading
    ) {
        TVShowByCategoryContent(
            tvShows,
            state = state, listener = viewModel
        )
    }
}

private fun categoriesReceiveEffect(
    navController: NavController,
    effect: MediaByCategoryScreenEffect
) {
    when (effect) {
        MediaByCategoryScreenEffect.NavigateBack -> navController.popBackStack()
        is MediaByCategoryScreenEffect.NavigateToMediaDetails -> {
            navController.navigate(
                TVShowDetailsDestination(
                    tvShowId = effect.mediaId,
                )
            )
        }
    }
}

@Composable
fun TVShowByCategoryContent(
    tvShows: LazyPagingItems<TVShowUiState>,
    state: TVShowByCategoryUiState,
    listener: MediaByCategoryInteractionListener,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        TopBar(
            modifier = Modifier
                .statusBarsPadding()
                .padding(vertical = 8.dp), title = {
                Text(
                    text  = stringResource(R.string.tv_shows),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title,
                )
            }
        )
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(R.string.loading)
                )
            }
        }
        TvShowsByCategoryResultGrid(
            categories = state.tvShowGenres,
            onCategoryCardClicked = listener::onCategoryCardClicked,
            onTvShowCardClicked = listener::onMediaCardClicked,
            mediaList = tvShows,
        )
    }
}


@Composable
private fun TvShowsByCategoryResultGrid(
    categories: List<GenreUiState>,
    modifier: Modifier = Modifier,
    onCategoryCardClicked: (Long) -> Unit,
    mediaList: LazyPagingItems<TVShowUiState>,
    onTvShowCardClicked: (Long) -> Unit,
) {
    Row(modifier = modifier.fillMaxSize()) {
        GenreChipsColumn(
            genres = categories,
            onGenreClick = {
                onCategoryCardClicked(it)
            },
            modifier = Modifier
                .width(102.dp)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            columns = Adaptive(minSize = 242.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(count = mediaList.itemCount) { index ->
                val media = mediaList[index]
                if (media != null) {
                    MediaCard(
                        modifier = Modifier.height(196.dp),
                        mediaImg = media.posterUrl,
                        title = media.title,
                        typeOfMedia = MediaType.TV_SHOW.name,
                        date = media.releaseDate,
                        rating = media.rating,
                        onClick = {
                            onTvShowCardClicked(
                                media.id
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreChipsColumn(
    genres: List<GenreUiState>,
    onGenreClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = genres,
            key = { it.id }
        ) { genre ->
            Chips(
                title = genre.name,
                icon = painterResource(getTvShowCategoryIcon(genre.id)),
                isSelected = genre.isSelected,
                onClick = { onGenreClick(genre.id.toLong()) }
            )
        }
    }
}
