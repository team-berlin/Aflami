package com.berlin.aflami.viewmodel.listFeature

interface ListScreenInteractionListener {
    fun onBackClicked()
    fun onAddClicked()
    fun onClickListCard(listId:Int)
}