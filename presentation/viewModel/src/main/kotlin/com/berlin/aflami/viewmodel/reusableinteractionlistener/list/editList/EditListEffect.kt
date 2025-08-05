package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList

sealed interface EditListEffect {
    data class ShowSuccessfulSnackBar(val isListEditedSuccessfully: Boolean) : EditListEffect
}