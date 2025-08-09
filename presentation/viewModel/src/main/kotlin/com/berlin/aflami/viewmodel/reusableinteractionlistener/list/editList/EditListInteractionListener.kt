package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList

interface EditListInteractionListener {
    fun onCancelEditingListClicked()
    fun onOldListTitleChanged(editedListTitle: String)
    fun onSaveOldListTitleToNewTitleClicked(listId: Int, editedListTitle: String)
}