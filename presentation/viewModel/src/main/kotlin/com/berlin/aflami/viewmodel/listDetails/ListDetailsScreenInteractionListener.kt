package com.berlin.aflami.viewmodel.listDetails

import androidx.compose.ui.text.input.TextFieldValue

interface ListDetailsScreenInteractionListener {
    fun onBackClicked()
    fun onRenameClicked(listId: Int, listTitle: TextFieldValue)
    fun onDeleteIconClicked(listId: Int)
    fun onDeleteDialogDismiss()
    fun onDeleteConfirmed(listId: Int)
    fun onMovieCardClicked(movieId: Long)
    fun onRemoveMovieClicked(listId: Int, movieId: Long)
}