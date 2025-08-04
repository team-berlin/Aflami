package com.berlin.aflami.viewmodel.listDetails

interface ListDetailsScreenInteractionListener {
    fun onBackClicked()
    fun onRenameClicked(listId:Int)
    fun onDeleteClicked(listId:Int)
}