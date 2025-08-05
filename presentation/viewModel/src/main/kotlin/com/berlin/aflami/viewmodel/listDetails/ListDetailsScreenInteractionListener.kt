package com.berlin.aflami.viewmodel.listDetails

interface ListDetailsScreenInteractionListener {
    fun onBackClicked()
    fun onRenameClicked(listId:Int)
    fun onDeleteClicked(listId:Int)
    fun onDeleteDialogDismiss()
    fun onDeleteConfirmed()
    fun onMovieCardClicked(movieId:Long)
    fun onRemoveMovieClicked(movieId: Long)
}