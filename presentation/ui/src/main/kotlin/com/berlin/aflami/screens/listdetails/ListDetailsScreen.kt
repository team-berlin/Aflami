package com.berlin.aflami.screens.listdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.berlin.aflami.screens.search.components.CountryTourExploring
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
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .navigationBarsPadding()
                .statusBarsPadding()
        ) {
            DefaultBar(
                title = listDetailsScreenState.listTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .statusBarsPadding(),
                firstOption = painterResource(R.drawable.edit),
                lastOption = painterResource(R.drawable.delete),
                lastOptionIconTint = Theme.color.statusColors.redAccent,
                optionContainerColor = Theme.color.surfaceHigh,
                onFirstOptionClicked = {
                    listener.onRenameClicked(
                        listDetailsScreenState.listId!!,
                        listDetailsScreenState.listTitle
                    )
                },
                onLastOptionClicked = { listener.onDeleteIconClicked(listDetailsScreenState.listId!!) },
                onNavigateBackClicked = listener::onBackClicked
            )
            AnimatedVisibility(
                enter =  EnterTransition.None ,
                exit = ExitTransition.None ,
                visible = listDetailsScreenState.isScreenLoading || movies.loadState.refresh is LoadState.Loading
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(), text = stringResource(R.string.loading)
                )
            }
            AnimatedVisibility(
                enter =  EnterTransition.None ,
                exit = ExitTransition.None ,
                visible = movies.loadState.refresh is LoadState.Error
            ) {
                NoInternetConnectionPlaceholder()
            }
            AnimatedVisibility(
                enter =  EnterTransition.None ,
                exit = ExitTransition.None ,
                visible = movies.itemCount == 0 && movies.loadState.refresh is LoadState.NotLoading
            ) {
                CountryTourExploring(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    image = painterResource(R.drawable.no_items_found),
                    R.string.no_saved_items_here,
                )
            }
            AnimatedVisibility(
                enter =  EnterTransition.None ,
                exit = ExitTransition.None ,
                visible = movies.itemCount != 0 && movies.loadState.refresh is LoadState.NotLoading
            ) {
                MoviesListItem(
                    movies = movies,
                    listId = listDetailsScreenState.listId
                        ?: throw IllegalArgumentException("no list if found!"),
                    onClickMovie = listener::onMovieCardClicked,
                    onClickDislikeItem = listener::onRemoveMovieClicked
                )
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
                    listTitle = effect.listTitle
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

        ListDetailsScreenEffect.ShowDeleteMovieFromListFailedSnackBar -> {}
        ListDetailsScreenEffect.ShowDeleteMovieFromListSucceededSnackBar -> {}
    }
}