package com.berlin.aflami.viewmodel.listFeature

interface ListScreenInteractionListener {
    fun onBackClicked()
    fun onCreateNewListClicked()
    fun onClickListCard(listId:Int)
}