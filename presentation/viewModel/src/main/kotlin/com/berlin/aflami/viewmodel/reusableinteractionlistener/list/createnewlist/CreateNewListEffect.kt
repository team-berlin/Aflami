package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist

sealed interface CreateNewListEffect {
    data class ShowSuccessfulSnackBar(val isListCreatedSuccessfully: Boolean) : CreateNewListEffect
}