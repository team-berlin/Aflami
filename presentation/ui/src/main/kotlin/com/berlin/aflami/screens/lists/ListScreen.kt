package com.berlin.aflami.screens.lists

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.component.SnackBar
import com.berlin.aflami.component.SnackBarStatus
import com.berlin.aflami.navigation.ListDetailsDestination
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.NavigationBarDestinations
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.listdetails.component.CreateNewListDialog
import com.berlin.aflami.screens.listdetails.component.EditListDialog
import com.berlin.aflami.screens.lists.component.ListCard
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.movie.SNACK_BAR_STATUS
import com.berlin.aflami.viewmodel.list.ListScreenEffect
import com.berlin.aflami.viewmodel.list.ListScreenInteractionListener
import com.berlin.aflami.viewmodel.list.ListScreenState
import com.berlin.aflami.viewmodel.list.ListScreenViewModel
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.ui.R


@Composable
fun ListScreen(
    modifier: Modifier = Modifier, listScreenViewModel: ListScreenViewModel = hiltViewModel(),
) {
    val listScreenState = listScreenViewModel.state.collectAsStateWithLifecycle()
    val navController = Theme.navController
    LaunchedEffect(Unit) {
        listScreenViewModel.effect.collect { listScreenEffect ->
            onReceiveNewEffect(navController = navController, effect = listScreenEffect)
        }
    }
    ListsContent(listScreenState = listScreenState.value, interactionListener = listScreenViewModel)
}

@Composable
private fun ListsContent(
    modifier: Modifier = Modifier,
    listScreenState: ListScreenState,
    interactionListener: ListScreenInteractionListener,
) {
    val favouriteLists: LazyPagingItems<FavouriteListItemUiState> =
        listScreenState.favouriteList.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .navigationBarsPadding()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .zIndex(10f)
        ) {
            when (listScreenState.snackBar.snackBarStatus) {
                SNACK_BAR_STATUS.LIST_RENAMED -> {
                    if (listScreenState.snackBar.isOperationSucceeded) {
                        SnackBar(
                            isVisible = listScreenState.snackBar.isVisible,
                            status = SnackBarStatus.SUCCESS,
                            text = stringResource(R.string.list_edit_successfully),
                            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.success),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = {
                                interactionListener.dismissSnackBar()
                            })
                    } else {
                        SnackBar(
                            isVisible = listScreenState.snackBar.isVisible,
                            status = SnackBarStatus.ERROR,
                            text = stringResource(
                                R.string.list_failed_to_edit
                            ),
                            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.error),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = {
                                interactionListener.dismissSnackBar()
                            })
                    }
                }

                SNACK_BAR_STATUS.LIST_DELETED ->
                    if (listScreenState.snackBar.isOperationSucceeded) {
                        SnackBar(
                            isVisible = listScreenState.snackBar.isVisible,
                            status = SnackBarStatus.SUCCESS,
                            text = stringResource(com.berlin.ui.R.string.list_deleted_successfully),
                            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.success),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = { interactionListener.dismissSnackBar() })
                    } else {
                        SnackBar(
                            isVisible = listScreenState.snackBar.isVisible,
                            status = SnackBarStatus.ERROR,
                            text = stringResource(com.berlin.ui.R.string.list_failed_to_deleted),
                            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.error),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = {
                                interactionListener.dismissSnackBar()
                            })
                    }

                SNACK_BAR_STATUS.CREATE_NEW_LIST -> {
                    if (listScreenState.snackBar.isOperationSucceeded) {
                        SnackBar(
                            isVisible = listScreenState.snackBar.isVisible,
                            status = SnackBarStatus.SUCCESS,
                            text = stringResource(com.berlin.ui.R.string.new_list_created),
                            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.success),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = { interactionListener.dismissSnackBar() })
                    } else {
                        SnackBar(
                            isVisible = listScreenState.snackBar.isVisible,
                            status = SnackBarStatus.ERROR,
                            text = stringResource(com.berlin.ui.R.string.create_new_list_failed),
                            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.error),
                            modifier = Modifier.align(Alignment.TopCenter),
                            onDismiss = {
                                interactionListener.dismissSnackBar()
                            })
                    }
                }

                SNACK_BAR_STATUS.ADD_MOVIE_TO_LIST -> {}
                null -> {}
            }
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = listScreenState.createNewListSheetState.isCreateNewListDialogVisible
        ) {
            CreateNewListDialog(
                listName = listScreenState.createNewListSheetState.newListTitle,
                onListNameChanged = interactionListener::onListNameChange,
                onCreateListClick = interactionListener::onCreateNewListClicked,
                onDismiss = interactionListener::onCancelCreatingNewListClicked,
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = listScreenState.editListSheetState.isEditNewListDialogVisible
        ) {
            EditListDialog(
                listId = listScreenState.editListSheetState.requiredListIdToEdit!!,
                listName = listScreenState.editListSheetState.currentListTitle,
                onListNameChanged = {
                    interactionListener.onOldListTitleChanged(
                        it.text
                    )
                },
                onSaveClick = interactionListener::onSaveOldListTitleToNewTitleClicked,
                onDismiss = interactionListener::onCancelEditingListClicked,
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = favouriteLists.loadState.refresh !is LoadState.Loading && listScreenState.isUserLoggedIn == true && favouriteLists.loadState.refresh is LoadState.Error,
        ) {
            NoInternetConnectionPlaceholder(
                onClick = interactionListener::onClickRetryFetchList
            )
        }
        AnimatedVisibility(
            enter = fadeIn(), exit = fadeOut(), visible = listScreenState.isScreenLoading
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(), text = stringResource(R.string.loading)
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = listScreenState.isLoginRequiredDialogVisible
        ) {
            LoginRequiredDialog(
                title = "Lists",
                onLoginClick = interactionListener::onLoginClicked,
                onDismiss = interactionListener::onBackClicked,
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = (favouriteLists.loadState.refresh !is LoadState.Loading
                    && listScreenState.isUserLoggedIn == true)
                    && favouriteLists.loadState.refresh !is LoadState.Error,
        ) {
            CountryTourExploring(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                image = painterResource(R.drawable.no_items_found),
                R.string.no_lists_yet,
                R.string.our_brain_is_still_empty_click_on_and_start_saving_your_favorite_items_and_shows_you_love
            )
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = favouriteLists.itemCount != 0 && listScreenState.isUserLoggedIn == true
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DefaultBar(
                    title = stringResource(R.string.lists),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    showNavigateBackButton = false,
                    lastOption = painterResource(R.drawable.add),
                    optionContainerColor = Theme.color.surfaceHigh,
                    onLastOptionClicked = interactionListener::onClickAddList,
                )
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 156.dp),
                    state = rememberLazyGridState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 64.dp),
                ) {
                    items(
                        favouriteLists.itemCount,
                        key = { index -> favouriteLists[index]?.listId!! }) { index ->
                        val item = favouriteLists[index]
                        item?.let {
                            ListCard(
                                title = it.listTitle,
                                count = it.numberOfFavouriteMovies,
                                modifier = modifier
                                    .size(156.dp, 147.dp)
                                    .clickable {
                                        interactionListener.onClickListCard(
                                            it.listId!!, it.listTitle
                                        )
                                    })
                        }
                    }
                }
            }
        }
    }
}

private fun onReceiveNewEffect(effect: ListScreenEffect, navController: NavController) {
    when (effect) {

        ListScreenEffect.NavigateBack -> navController.navigate(NavigationBarDestinations.HomeScreen)
        is ListScreenEffect.NavigateToSeeAllListScreen -> navController.navigate(
            ListDetailsDestination(listId = effect.listId, listTitle = effect.listTitle)
        )

        ListScreenEffect.NavigateToLoginScreen -> navController.navigate(route = LoginDestination)
    }
}