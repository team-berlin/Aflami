package com.berlin.aflami.viewmodel.listDetails

sealed interface ListDetailsScreenEffect {
    object NavigateToDeleteListSheet : ListDetailsScreenEffect
    object NavigateToEditListSheet : ListDetailsScreenEffect
    object NavigateToCreateNewListSheet : ListDetailsScreenEffect
    object NavigateToMovieDetails : ListDetailsScreenEffect
}