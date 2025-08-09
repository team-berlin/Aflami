package com.berlin.aflami.viewmodel.listFeature

sealed interface ListScreenEffect {
    object NavigateBack : ListScreenEffect
    object NavigateToLoginScreen : ListScreenEffect
    data class NavigateToSeeAllListScreen(val listId: Int, val listTitle: String) : ListScreenEffect
    data class ShowEditListStatusSnackBar(val isListEditedSuccessfully: Boolean) : ListScreenEffect
    data class ShowListDeletedSnackBar(val isListDeletedSuccessfully: Boolean) : ListScreenEffect
    data class ShowCreateNewListStatusSnackBar(val isListCreatedSuccessfully: Boolean) :
        ListScreenEffect
}