package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.base.BaseViewModel

class ListScreenViewModel(
    private val create
) : BaseViewModel<ListScreenState, ListScreenEffect>(ListScreenState()),
    ListScreenInteractionListener {
    override fun onBackClicked() = sendNewEffect(ListScreenEffect.NavigateBack)

    override fun onCreateNewListClicked() {
        TODO("Not yet implemented")
    }

    override fun onClickListCard(listId: Int) {
        TODO("Not yet implemented")
    }

}