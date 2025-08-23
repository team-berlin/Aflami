package com.berlin.aflami.screens.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.search.search.Chips
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryInteractionListener
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryScreenEffect
import com.berlin.aflami.viewmodel.categories.tvshow.TVShowByCategoryScreenViewModel
import com.berlin.aflami.viewmodel.categories.tvshow.TVShowByCategoryUiState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.aflami.viewmodel.util.toEnglishDigits
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
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = state.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }
    val tvShows = state.tvShowsPagingDataFlow.collectAsLazyPagingItems()
    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
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
                    text = stringResource(R.string.tv_shows),
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
            }
        )

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
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    )
    {
        GenreChipsColumn(
            genres = categories,
            onGenreClick = {
                onCategoryCardClicked(it)
            },
            modifier = Modifier
                .fillMaxHeight()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
        )
        Box(Modifier.fillMaxSize())
        {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = Adaptive(minSize = 242.dp),
                contentPadding = PaddingValues(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mediaList.itemCount) { index ->
                    val media = mediaList[index]
                    if (media != null) {
                        MediaCard(
                            modifier = Modifier.height(196.dp),
                            mediaImg = media.posterUrl,
                            title = media.title,
                            typeOfMedia = MediaType.TV_SHOW.name,
                            date = media.releaseDate,
                            rating = media.rating.toEnglishDigits(),
                            onClick = { onTvShowCardClicked(media.id) }
                        )
                    }
                }
            }
            when {
                mediaList.loadState.refresh is LoadState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    text = stringResource(R.string.loading)
                )

                mediaList.loadState.refresh is LoadState.Error -> {
                    NoInternetConnectionPlaceholder(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { mediaList.retry() }
                    )
                }

                mediaList.itemCount == 0 && mediaList.loadState.refresh !is LoadState.Error -> {
                    NoItemsFound(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}

@Composable
fun NoItemsFound(modifier: Modifier) {
    Column(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.no_items_found),
            contentDescription = stringResource(R.string.no_items_found),
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.no_items_found),
            style = Theme.textStyle.body.medium,
            textAlign = TextAlign.Center,
            color = Theme.color.textColors.title
        )
    }
}

@Composable
private fun GenreChipsColumn(
    genres: List<GenreUiState>,
    onGenreClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    val selectedIndex = genres.indexOfFirst { it.isSelected }.coerceAtLeast(0)

    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        listState.animateScrollToItem(selectedIndex)
    }
    LazyColumn(
        modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = genres,
            key = { it.id }
        ) { genre ->
            Box(Modifier.size(height = 96.dp, width = 70.dp)) {
                Chips(
                    title = genre.name,
                    icon = painterResource(getTvShowCategoryIcon(genre.id)),
                    isSelected = genre.isSelected,
                    onClick = { onGenreClick(genre.id.toLong()) }
                )
            }
        }
    }
}
