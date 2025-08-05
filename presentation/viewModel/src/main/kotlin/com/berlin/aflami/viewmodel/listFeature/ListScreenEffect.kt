package com.berlin.aflami.viewmodel.listFeature

sealed interface ListScreenEffect{
    data class NavigateToSeeAllListScreen(val listId: Int) : ListScreenEffect
}