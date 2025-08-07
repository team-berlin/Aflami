package com.berlin.aflami.screens.listdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.listdetails.component.DeleteListDialog
import com.berlin.aflami.screens.listdetails.component.MoviesListItem
import com.berlin.aflami.screens.search.components.NoDataContainer
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenState
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenViewModel
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
            firstOption = painterResource(R.drawable.edit),
            lastOption = painterResource(R.drawable.delete),
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

@Preview
@Composable
private fun PreviewListDetailsContent() {
    AflamiTheme {
        ListDetailsContent(
            state = ListDetailsScreenState(),
            listener = object : ListDetailsScreenInteractionListener {
                override fun onMovieCardClicked(id: Long) {}
                override fun onRemoveMovieClicked(id: Long) {}
                override fun onBackClicked() {
                    TODO("Not yet implemented")
                }

                override fun onRenameClicked(listId: Int) {
                    TODO("Not yet implemented")
                }

                override fun onDeleteIconClicked(listId: Int) {
                    TODO("Not yet implemented")
                }

                override fun onDeleteDialogDismiss() {}
                override fun onDeleteConfirmed() {}
            }
        )
    }

}