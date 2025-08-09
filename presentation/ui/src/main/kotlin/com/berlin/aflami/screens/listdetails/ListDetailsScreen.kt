package com.berlin.aflami.screens.listdetails

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.navigation.ListsScreenWithArgs
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.NavigationBarDestinations
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.listdetails.component.DeleteListDialog
import com.berlin.aflami.screens.listdetails.component.MoviesListItem
import com.berlin.aflami.screens.search.components.NoDataContainer
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenEffect
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenState
import com.berlin.aflami.viewmodel.listDetails.ListDetailsScreenViewModel
import com.berlin.ui.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ListDetailsScreen(
    listDetailsViewModel: ListDetailsScreenViewModel = hiltViewModel(),
) {

    val listDetailsScreenState by listDetailsViewModel.state.collectAsStateWithLifecycle()
    val navController = Theme.navController
    LaunchedEffect(Unit) {
        listDetailsViewModel.effect.collectLatest { listDetailsScreenEffect ->
            onReceiveNewEffect(navController = navController, effect = listDetailsScreenEffect)
        }
    }

    ListDetailsContent(
        listDetailsScreenState = listDetailsScreenState, listener = listDetailsViewModel
    )

}

@Composable
private fun ListDetailsContent(
    listDetailsScreenState: ListDetailsScreenState,
    listener: ListDetailsScreenInteractionListener,
    modifier: Modifier = Modifier,
) {
    val movies = listDetailsScreenState.listItems.collectAsLazyPagingItems()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .navigationBarsPadding()
    ) {
        DefaultBar(
            title = listDetailsScreenState.listTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, 8.dp),
            firstOption = painterResource(R.drawable.edit),
            lastOption = painterResource(R.drawable.delete),
            onFirstOptionClicked = { listener.onRenameClicked(listDetailsScreenState.listId!!) },
            onLastOptionClicked = { listener.onDeleteIconClicked(listDetailsScreenState.listId!!) },
        )
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = listDetailsScreenState.isScreenLoading || movies.loadState.refresh is LoadState.Loading
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(), text = stringResource(R.string.loading)
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
            Log.d(
                "khairy",
                "no data because result = ${movies.itemCount == 0 && movies.loadState.refresh is LoadState.NotLoading}"
            )
            NoDataContainer(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                image = painterResource(R.drawable.no_items_found),
                R.string.no_saved_items_here,
                R.string.you_can_add_items_to_your_list_by_searching_for_them_in_the_app
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = movies.itemCount != 0 && movies.loadState.refresh is LoadState.NotLoading
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                AnimatedVisibility(
                    enter = fadeIn(), exit = fadeOut(), visible = movies.itemCount > 0
                ) {
                    MoviesListItem(
                        movies = movies,
                        listId = listDetailsScreenState.listId
                            ?: throw IllegalArgumentException("no list if found!"),
                        modifier = Modifier.fillMaxSize(),
                        onClickMovie = listener::onMovieCardClicked,
                        onClickDislikeItem = listener::onRemoveMovieClicked
                    )
                }
            }
        }
    }
    AnimatedVisibility(
        visible = listDetailsScreenState.showDeleteListDialog, enter = fadeIn(), exit = fadeOut()
    ) {
        DeleteListDialog(
            onDismiss = listener::onDeleteDialogDismiss,
            onConfirm = listener::onDeleteConfirmed,
            listId = listDetailsScreenState.listId!!
        )
    }
}

private fun onReceiveNewEffect(effect: ListDetailsScreenEffect, navController: NavController) {
    when (effect) {
        ListDetailsScreenEffect.NavigateBack -> navController.popBackStack()
        is ListDetailsScreenEffect.NavigateBackAndShowDeleteListStatusSnackBar -> {
            Log.d("Khairy", "navigating to ListScreen to show deleted list snack bar")

            navController.navigate(
                route = ListsScreenWithArgs(
                    showDeletedSnackBar = true,
                    isListDeletedSuccessfully = effect.isListDeletedSuccessfully
                )
            ) {
                popUpTo<NavigationBarDestinations.ListsScreenNoArgs> {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }

        is ListDetailsScreenEffect.NavigateToAllListsScreenAndShowEditListSheet -> {
            navController.navigate(
                route = ListsScreenWithArgs(
                    showEditSheet = true,
                    requiredToEditListId = effect.listId,
                )
            ) {
                popUpTo<NavigationBarDestinations.ListsScreenNoArgs> {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }

        is ListDetailsScreenEffect.NavigateToMovieDetailsScreen -> {
            navController.navigate(MovieDetailsDestination(effect.movieId))
        }

        ListDetailsScreenEffect.ShowDeleteMovieFromListFailedSnackBar -> TODO()
        ListDetailsScreenEffect.ShowDeleteMovieFromListSucceededSnackBar -> TODO()
    }
}
//@Preview
//@Composable
//private fun PreviewListDetailsContent() {
//    AflamiTheme {
//        ListDetailsContent(
//            state = ListDetailsScreenState(),
//        )
//    }
//}