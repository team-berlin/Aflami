package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListInteractionListener

interface ListScreenInteractionListener : CreateNewListInteractionListener {
    fun onBackClicked()
    fun onCreateNewListClicked()
    fun onClickListCard(listId:Int)
}