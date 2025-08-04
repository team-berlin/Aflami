package com.berlin.aflami.viewmodel.list

sealed interface ListScreenEffect {
    data class NavigateToSeeAllListScreen(val listId: Int) : ListScreenEffect
}