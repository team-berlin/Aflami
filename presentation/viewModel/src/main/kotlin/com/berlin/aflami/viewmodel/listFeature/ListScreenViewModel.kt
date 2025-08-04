package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.base.BaseViewModel

class ListScreenViewModel : BaseViewModel<ListScreenState, ListScreenEffect>(ListScreenState()),
    ListScreenInteractionListener {
    override fun onBackClicked() {
        TODO("Not yet implemented")
    }

    override fun onCreateNewListClicked() {
        TODO("Not yet implemented")
    }

    override fun onClickListCard(listId: Int) {
        TODO("Not yet implemented")
    }

}