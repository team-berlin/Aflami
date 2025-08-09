package com.berlin.aflami.viewmodel.listDetails

interface ListDetailsScreenInteractionListener {
    fun onBackClicked()
    fun onRenameClicked(listId: Int)
    fun onDeleteIconClicked(listId: Int)
    fun onDeleteDialogDismiss()
    fun onDeleteConfirmed(listId: Int)
    fun onMovieCardClicked(movieId: Long)
    fun onRemoveMovieClicked(listId: Int, movieId: Long)
}