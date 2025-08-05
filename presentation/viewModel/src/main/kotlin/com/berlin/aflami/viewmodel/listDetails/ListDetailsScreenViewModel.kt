package com.berlin.aflami.viewmodel.listDetails

import com.berlin.aflami.viewmodel.base.BaseViewModel

class ListDetailsScreenViewModel : BaseViewModel<ListDetailsScreenState, ListDetailsScreenEffect>(
    ListDetailsScreenState()
), ListDetailsScreenInteractionListener {

    init {

    }
    override fun onBackClicked() {
        TODO("Not yet implemented")
    }

    override fun onRenameClicked(listId: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClicked(listId: Int) {
        TODO("Not yet implemented")
    }

    override fun onMovieCardClicked(movieId: Long) {
        TODO("Not yet implemented")
    }

    override fun onTVShowCardClicked(tvShowId: Long) {
        TODO("Not yet implemented")
    }
}