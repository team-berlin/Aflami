package com.berlin.aflami.viewmodel.listDetails

import com.berlin.aflami.viewmodel.base.BaseViewModel
import usecase.favouritelist.DeleteUserFavouriteListUseCase
import usecase.favouritelist.GetFavouriteListItemsUseCase

class ListDetailsScreenViewModel(
    private val getAllFavouriteListItemsUseCase: GetFavouriteListItemsUseCase,
    private val deleteUserFavouriteListUseCase: DeleteUserFavouriteListUseCase,
) : BaseViewModel<ListDetailsScreenState, ListDetailsScreenEffect>(
    ListDetailsScreenState()
), ListDetailsScreenInteractionListener {

    init {
        getAllFavoriteListItems()
    }

    private fun getAllFavoriteListItems() {
        TODO("Not yet implemented")
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

    override fun onRemoveMovieClicked(movieId: Long) {
        TODO("Not yet implemented")
    }

}