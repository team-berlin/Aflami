package com.berlin.aflami.viewmodel.list

sealed interface ListScreenEffect {
    object NavigateBack : ListScreenEffect
    object NavigateToLoginScreen : ListScreenEffect
    data class NavigateToSeeAllListScreen(val listId: Int, val listTitle: String) : ListScreenEffect
}