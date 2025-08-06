package com.berlin.aflami.viewmodel.listDetails

sealed interface ListDetailsScreenEffect {
    object NavigateBack : ListDetailsScreenEffect
    object NavigateToDeleteListSheet : ListDetailsScreenEffect
    object NavigateToEditListSheet : ListDetailsScreenEffect
    object NavigateToCreateNewListSheet : ListDetailsScreenEffect
    object NavigateToMovieDetails : ListDetailsScreenEffect
    data class NavigateBackAndShowDeleteListStatusSnackBar(val isListDeletedSuccessfully: Boolean) :
        ListDetailsScreenEffect

    data class NavigateToAllListsScreenAndShowEditListSheet(val listId: Int) :
        ListDetailsScreenEffect
}