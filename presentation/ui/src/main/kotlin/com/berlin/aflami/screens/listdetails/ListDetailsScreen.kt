package com.berlin.aflami.screens.listdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.IconButton
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.search.components.NoDataContainer
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenState
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenViewModel
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.ui.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ListDetailsScreen(
    listDetailsViewModel: ListDetailsScreenViewModel = hiltViewModel()
) {

    val state by listDetailsViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        listDetailsViewModel.effect.collectLatest {

        }
    }

    ListDetailsContent(
        state = state, listener = listDetailsViewModel
    )

}

@Composable
private fun ListDetailsContent(
    state: ListDetailsScreenState,
    listener: ListDetailsScreenInteractionListener,
) {
    val movies = state.listItems.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        DefaultBar(
            title = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, 8.dp),
            firstOption = painterResource(com.berlin.ui.R.drawable.edit),
            lastOption = painterResource(com.berlin.ui.R.drawable.delete),
            onFirstOptionClicked = {},
            onLastOptionClicked = {},
        )

        AnimatedVisibility(
            enter = fadeIn(), exit = fadeOut(), visible = state.isScreenLoading
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(com.berlin.ui.R.string.loading)
            )
        }

        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = movies.loadState.refresh is LoadState.Error
        ) {
            NoInternetConnectionPlaceholder()
        }

        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = movies.itemCount == 0 && movies.loadState.refresh is LoadState.NotLoading
        ) {
            NoDataContainer(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                image = painterResource(R.drawable.no_items_found),
                R.string.no_saved_items_here,
                R.string.you_can_add_items_to_your_list_by_searching_for_them_in_the_app
            )
        }


        AnimatedVisibility(
            enter = fadeIn(), exit = fadeOut(), visible = movies.itemCount > 0
        ) {
            MoviesListItem(
                movies = movies,
                modifier = Modifier.fillMaxSize(),
                onClickMovie = listener::onMovieCardClicked,
                onClickDislikeItem = listener::onRemoveMovieClicked
            )
        }
    }

    AnimatedVisibility(
        visible = state.showDeleteListDialog, enter = fadeIn(), exit = fadeOut()
    ) {
        DeleteListDialog(
            onDismiss = listener::onDeleteDialogDismiss, onConfirm = listener::onDeleteConfirmed
        )
    }

}

@Composable
fun MoviesListItem(
    movies: LazyPagingItems<MovieUiState>,
    modifier: Modifier = Modifier,
    onClickMovie: (Long) -> Unit,
    onClickDislikeItem: (Long) -> Unit = { }
) {
    val itemState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        state = itemState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = movies.itemCount, key = movies.itemKey { it.id }) { index ->
            val movie = movies[index] ?: return@items
            Box {
                MediaCard(
                    mediaImg = movie.posterUrl,
                    title = movie.title,
                    date = movie.releaseDate,
                    rating = movie.rating,
                    typeOfMedia = stringResource(R.string.movie),
                    onClick = { onClickMovie(movie.id) })

                IconButton(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp)
                        .size(32.dp),
                    paddingValues = PaddingValues(6.dp),
                    painter = painterResource(com.berlin.ui.R.drawable.heart),
                    contentDescription = null,
                    onClick = { onClickDislikeItem(movie.id) },
                )
            }
        }
    }
}

@Composable
private fun DeleteListDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogTitleBar(
                titleResource = R.string.delete_list,
                modifier = Modifier.fillMaxWidth(),
                onDismiss = onDismiss
            )
            Image(
                painter = painterResource(R.drawable.deletealert),
                contentDescription = stringResource(R.string.delete_list),
                modifier = Modifier.height(100.dp),
                contentScale = ContentScale.FillHeight
            )

            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(R.string.delete_confirm),
                style = Theme.textStyle.title.small,
                color = Theme.color.textColors.body,
                textAlign = TextAlign.Center
            )

            PrimaryButton(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                containerColor = Theme.color.primaryVariant
            ) {
                Text(
                    stringResource(R.string.delete),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.primary
                )
            }

        }
    }
}


@Composable
private fun DialogTitleBar(
    titleResource: Int, modifier: Modifier = Modifier, onDismiss: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(titleResource),
            style = Theme.textStyle.title.large,
            color = Theme.color.textColors.title
        )
        IconButton(
            painter = painterResource(com.berlin.designsystem.R.drawable.cancel),
            contentDescription = "Cancel",
            onClick = { onDismiss() },
            tint = Theme.color.textColors.title
        )
    }
}