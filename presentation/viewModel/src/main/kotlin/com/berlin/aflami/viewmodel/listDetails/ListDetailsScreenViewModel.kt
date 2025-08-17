package com.berlin.aflami.viewmodel.listDetails

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.util.ListCountEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import usecase.favouritelist.DeleteMovieFromUserFavouriteListUseCase
import usecase.favouritelist.DeleteUserFavouriteListUseCase
import javax.inject.Inject

@HiltViewModel
class ListDetailsScreenViewModel @Inject constructor(
    private val deleteMovieFromUserFavouriteListUseCase: DeleteMovieFromUserFavouriteListUseCase,
    private val deleteUserFavouriteListUseCase: DeleteUserFavouriteListUseCase,
    private val favouriteMoviesPagingSourceFactory: FavouriteMoviesPagingSource.Factory,
    private val listCountEventBus: ListCountEventBus,
    favouriteListDetailsArgs: FavouriteListDetailsArgs,
) : BaseViewModel<ListDetailsScreenState, ListDetailsScreenEffect>(
    ListDetailsScreenState()
), ListDetailsScreenInteractionListener {

    private val favouriteListId: Int = favouriteListDetailsArgs.favouriteListId
        ?: throw IllegalArgumentException("list id is null")
    private val favouriteListTitle: String = favouriteListDetailsArgs.favouriteListTitle
        ?: throw IllegalArgumentException("list title is null")
    private var latestPagingSource: FavouriteMoviesPagingSource? = null

    init {
        updateState { screenState ->
            screenState.copy(
                listId = favouriteListId, listTitle = favouriteListTitle, isScreenLoading = true
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
        updateState { screenState ->
            screenState.copy(
                errorMessage = state1.message, isScreenLoading = false
            )
        }
    }

    private fun getFavouriteListMoviesAsFlow(favouriteListId: Int): Flow<PagingData<MovieUiState>> =
        Pager(
            config = defaultPageConfigurations(), pagingSourceFactory = {
                favouriteMoviesPagingSourceFactory.create(favouriteListId)
            }).flow.cachedIn(viewModelScope)

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(isScreenLoading = true) }

    private fun updateScreenWithNewFavouriteMovies(flowOfMoviesUiStates: Flow<PagingData<MovieUiState>>) {
        updateState { screenState ->
            screenState.copy(
                listItems = flowOfMoviesUiStates, isScreenLoading = false
            )
        }
    }

    //region renameAndDeleteListInteraction interactionListeners
    override fun onBackClicked() = sendNewEffect(ListDetailsScreenEffect.NavigateBack)

    override fun onRenameClicked(listId: Int, listTitle: TextFieldValue) =
        sendNewEffect(
            ListDetailsScreenEffect.NavigateToAllListsScreenAndShowEditListSheet(
                listId,
                listTitle = listTitle.text
            )
        )

    override fun onDeleteIconClicked(listId: Int) =
        updateState { screenState -> screenState.copy(showDeleteListDialog = true) }

    override fun onDeleteDialogDismiss() =
        updateState { screenState -> screenState.copy(showDeleteListDialog = false) }

    override fun onDeleteConfirmed(listId: Int) {
        updateState { screenState -> screenState.copy(showDeleteListDialog = false) }
        tryToCall(call = { deleteUserFavouriteListUseCase(listId = listId) }, onSuccess = {
            sendNewEffect(ListDetailsScreenEffect.NavigateBackAndShowDeleteListStatusSnackBar(true))
        }, onError = { errorUiState ->
            sendNewEffect(ListDetailsScreenEffect.NavigateBackAndShowDeleteListStatusSnackBar(false))
        })
    }
    //endregion

    override fun onMovieCardClicked(movieId: Long) =
        sendNewEffect(ListDetailsScreenEffect.NavigateToMovieDetailsScreen(movieId = movieId))


    override fun onRemoveMovieClicked(listId: Int, movieId: Long) {
        tryToCall(
            call = { deleteMovieFromUserFavouriteListUseCase(listId = listId, movieId = movieId) },
            onSuccess = {
                latestPagingSource?.invalidate()
                updateState { screenState ->
                    screenState.copy(numberOfFavouriteMovies = (screenState.numberOfFavouriteMovies - 1).coerceAtLeast(0))
                }
                viewModelScope.launch { listCountEventBus.emit(listId, -1) }
                getAllFavoriteListItems(favouriteListId = favouriteListId)
                sendNewEffect(ListDetailsScreenEffect.ShowDeleteMovieFromListSucceededSnackBar)
            },
            onError = { sendNewEffect(ListDetailsScreenEffect.ShowDeleteMovieFromListFailedSnackBar) },
        )
    }


}