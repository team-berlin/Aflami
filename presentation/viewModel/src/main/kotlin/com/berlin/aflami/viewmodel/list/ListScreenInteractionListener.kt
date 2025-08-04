package com.berlin.aflami.viewmodel.list

interface ListScreenInteractionListener {
    fun onBackClicked()
    fun onAddClicked()
    fun onClickListCard(listId:Int)
}