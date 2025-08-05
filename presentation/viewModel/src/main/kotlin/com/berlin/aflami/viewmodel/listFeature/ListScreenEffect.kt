package com.berlin.aflami.viewmodel.listFeature

sealed interface ListScreenEffect {
    object NavigateBack : ListScreenEffect
    data class NavigateToSeeAllListScreen(val listId: Int) : ListScreenEffect
    data class ShowEditListStatusSnackBar(val isListEditedSuccessfully: Boolean) : ListScreenEffect
    data class ShowCreateNewListStatusSnackBar(val isListCreatedSuccessfully: Boolean) :
        ListScreenEffect
}