package com.berlin.aflami.viewmodel.listDetails

import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import usecase.favouritelist.DeleteMovieFromUserFavouriteList
import usecase.favouritelist.GetFavouriteListItemsUseCase
import javax.inject.Inject

@HiltViewModel
class ListDetailsScreenViewModel @Inject constructor(
    private val getAllFavouriteListItemsUseCase: GetFavouriteListItemsUseCase,
    private val deleteMovieFromUserFavouriteList: DeleteMovieFromUserFavouriteList,
    favouriteListDetailsArgs: FavouriteListDetailsArgs,
) : BaseViewModel<ListDetailsScreenState, ListDetailsScreenEffect>(
    ListDetailsScreenState()
), ListDetailsScreenInteractionListener {

    private val favouriteListId: Int =
        favouriteListDetailsArgs.favouriteListId
            ?: throw IllegalArgumentException("list id is null")
    private val favouriteListTitle: String =
        favouriteListDetailsArgs.favouriteListTitle
            ?: throw IllegalArgumentException("list title is null")

    init {
        updateState { screenState ->
            screenState.copy(
                listId = favouriteListId,
                listTitle = favouriteListTitle
            )
        }
        getAllFavoriteListItems(favouriteListId = favouriteListId)
    }

    private fun getAllFavoriteListItems(favouriteListId: Int) {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                getFavouriteListMoviesAsFlow(favouriteListId)
            },
            onSuccess = ::updateScreenWithNewFavouriteMovies,
            onError = ::updateScreenStateWithErrorMessage
        )
    }

    private fun updateScreenStateWithErrorMessage(state1: ErrorUiState) {
        updateState { screenState -> screenState.copy(errorMessage = state1.message) }
    }

    private fun getFavouriteListMoviesAsFlow(favouriteListId: Int): Flow<PagingData<MovieUiState>> =
        Pager(
            config = defaultPageConfigurations(), pagingSourceFactory = {
                FavouriteMoviesPagingSource(
                    getAllFavouriteListItemsUseCase = getAllFavouriteListItemsUseCase,
                    favouriteListId = favouriteListId
                )
            }).flow

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(isScreenLoading = true) }

    private fun updateScreenWithNewFavouriteMovies(flowOfMoviesUiStates: Flow<PagingData<MovieUiState>>) {
        updateState { screenState -> screenState.copy(listItems = flowOfMoviesUiStates) }
    }

    //region renameAndDeleteListInteraction interactionListeners
    override fun onBackClicked() = sendNewEffect(ListDetailsScreenEffect.NavigateBack)

    override fun onRenameClicked(listId: Int) =
        sendNewEffect(ListDetailsScreenEffect.NavigateToEditListSheet)

    override fun onDeleteIconClicked(listId: Int) =
        sendNewEffect(ListDetailsScreenEffect.NavigateToDeleteListSheet)

    override fun onDeleteDialogDismiss() =
        sendNewEffect(ListDetailsScreenEffect.DismissDeleteDialog)

    override fun onDeleteConfirmed() {
        TODO("Not yet implemented")
    }
    //endregion

    override fun onMovieCardClicked(movieId: Long) =
        sendNewEffect(ListDetailsScreenEffect.NavigateToMovieDetailsScreen(movieId = movieId))

    override fun onRemoveMovieClicked(listId: Int, movieId: Long) {
        tryToCall(
            call = { deleteMovieFromUserFavouriteList(listId = listId, movieId = movieId) },
            onSuccess = { ListDetailsScreenEffect.ShowDeleteMovieFromListSucceededSnackBar },
            onError = { sendNewEffect(ListDetailsScreenEffect.ShowDeleteMovieFromListFailedSnackBar) },
        )
    }

}