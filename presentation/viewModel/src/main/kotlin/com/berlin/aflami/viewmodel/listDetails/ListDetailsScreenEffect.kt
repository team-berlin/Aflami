package com.berlin.aflami.viewmodel.listDetails

sealed interface ListDetailsScreenEffect {
    object NavigateBack : ListDetailsScreenEffect

    data class NavigateToMovieDetailsScreen(val movieId: Long) : ListDetailsScreenEffect
    object ShowDeleteMovieFromListFailedSnackBar : ListDetailsScreenEffect
    object ShowDeleteMovieFromListSucceededSnackBar : ListDetailsScreenEffect
    data class NavigateBackAndShowDeleteListStatusSnackBar(val isListDeletedSuccessfully: Boolean) :
        ListDetailsScreenEffect

    data class NavigateToAllListsScreenAndShowEditListSheet(
        val listId: Int,
        val listTitle: String,
    ) :
        ListDetailsScreenEffect
}