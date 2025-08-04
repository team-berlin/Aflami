package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.deleteList

sealed interface DeleteListEffect {
    data class ShowSuccessfulSnackBar(val isdDeletedSuccessfully: Boolean) : DeleteListEffect

}