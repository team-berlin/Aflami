package com.berlin.aflami.viewmodel.listFeature

sealed interface ListScreenEffect {
    object NavigateBack : ListScreenEffect
    object CancelCreatingNewList : ListScreenEffect
    object ShowCreateNewListSheet : ListScreenEffect
    data class NavigateToSeeAllListScreen(val listId: Int) : ListScreenEffect
    data class ShowEditListStatusSnackBar(val isListEditedSuccessfully: Boolean) : ListScreenEffect
    data class ShowCreateNewListStatusSnackBar(val isListCreatedSuccessfully: Boolean) :
        ListScreenEffect
}